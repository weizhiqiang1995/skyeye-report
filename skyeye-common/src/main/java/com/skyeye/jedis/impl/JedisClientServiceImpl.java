/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.jedis.impl;

import cn.hutool.json.JSONUtil;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.jedis.JedisClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.util.JedisClusterCRC16;
import redis.clients.util.Slowlog;

import java.util.*;
import java.util.Map.Entry;

/**
 * 
     * @ClassName: JedisClientCluster
     * @Description: redis集群
     * @author 卫志强
     * @date 2018年11月17日
     *
 */
@Service
public class JedisClientServiceImpl implements JedisClientService {
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Value("${redis.cluster}")  
    private String cluster;
	
	/**
	 * 默认key失效时间为十天
	 */
	private int TEN_DAY_SECONDS = 10 * 24 * 60 * 60;

	@Override
	public void set(String key, String value) {
		jedisCluster.set(key, value);
		//为防止缓存穿透，空值设置过期时间，2S后自动删除
		if(ToolUtil.isBlank(value)){
			expire(key, 2);
		}else{
			expire(key, TEN_DAY_SECONDS);
		}
	}
	
	@Override
	public void set(String key, String value, int seconds) {
		jedisCluster.set(key, value);
		//为防止缓存穿透，空值设置过期时间，2S后自动删除
		if(ToolUtil.isBlank(value)){
			expire(key, 2);
		}else{
			expire(key, seconds);
		}
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}
	
	@Override
	public Long incrByData(String key, long idata) {
		return jedisCluster.incrBy(key, idata);
	}
	
	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

	@Override
	public Long del(String key) {
        return jedisCluster.del(key);
	}
	
	@Override
	public void delKeys(String keysPattern) {
		Map<String, JedisPool> jedisPools = jedisCluster.getClusterNodes();
		Iterator<Map.Entry<String, JedisPool>> entries = jedisPools.entrySet().iterator(); 
		Jedis jedis = null;
		while (entries.hasNext()) {
			Entry<String, JedisPool> entry = entries.next(); 
			if(cluster.indexOf(entry.getKey()) != -1){
				jedis = entry.getValue().getResource();
				if (!jedis.info("replication").contains("role:slave")) {
					Set<String> keys = jedis.keys(keysPattern);
					if (keys.size() > 0) {
						Map<Integer, List<String>> map = new HashMap<>(6600);
						for (String key : keys) {
							int slot = JedisClusterCRC16.getSlot(key);
							// cluster模式执行多key操作的时候，这些key必须在同一个slot上，不然会报:JedisDataException: // CROSSSLOT // request // don't // hash to // the same // slot
							// 按slot将key分组，相同slot的key一起提交
							if (map.containsKey(slot)) {
								map.get(slot).add(key);
							} else {
								map.put(slot, Arrays.asList(key));
							}
						}
						for (Map.Entry<Integer, List<String>> integerListEntry : map.entrySet()) {
							jedis.del(integerListEntry.getValue().toArray(new String[integerListEntry.getValue().size()]));
						}
						jedis.close();
					}
				}
			}
		}
	}
	
	@Override
	public String getRedisInfo() {
		Jedis jedis = null;
		try {
			Map<String, JedisPool> jedisPools = jedisCluster.getClusterNodes();
			List<Map<String, Object>> ridMationList = new ArrayList<>();
			Iterator<Map.Entry<String, JedisPool>> entries = jedisPools.entrySet().iterator(); 
			while (entries.hasNext()) {
				Entry<String, JedisPool> entry = entries.next(); 
				if(cluster.indexOf(entry.getKey()) != -1){
					jedis = entry.getValue().getResource();
					Client client = jedis.getClient();
					client.info();
					String info = client.getBulkReply();
					jedis.close();
					List<Map<String, Object>> ridList = new ArrayList<>();
					Map<String, Object> redisMation = new HashMap<>();
					String[] strs = info.split("\n");
					Map<String, Object> bean = null;
					if (strs != null && strs.length > 0) {
						for (int i = 0; i < strs.length; i++) {
							String[] str = strs[i].split(":");
							if (str != null && str.length > 1) {
								bean = new HashMap<>();
								bean.put("key", str[0]);
								bean.put("value", str[1].replace("\n", "").replace("\r", ""));
								ridList.add(bean);
							}
						}
						redisMation.put("ip", entry.getKey());
						redisMation.put("mation", ridList);
						ridMationList.add(redisMation);
					}
				}
			}
			return JSONUtil.toJsonStr(ridMationList);
		} finally {
		}
	}

	@Override
	public List<Slowlog> getLogs(long entries) {
		return null;
	}

	@Override
	public Long getLogsLen() {
		return new Long(1);
	}

	@Override
	public String logEmpty() {
		return "";
	}

	@Override
	public Long dbSize() {
		return new Long(1);
	}

	@Override
	public List<Map<String, Object>> getClusterNodes() throws Exception {
		Map<String, JedisPool> jedisPools = jedisCluster.getClusterNodes();
		List<Map<String, Object>> ridPoolList = new ArrayList<>();
		Iterator<Map.Entry<String, JedisPool>> entries = jedisPools.entrySet().iterator(); 
		while (entries.hasNext()) {
			Entry<String, JedisPool> entry = entries.next(); 
			if(cluster.indexOf(entry.getKey()) != -1){
				Map<String, Object> redisPoolMation = new HashMap<>();
				redisPoolMation.put("ip", entry.getKey());
				ridPoolList.add(redisPoolMation);
			}
		}
		return ridPoolList;
	}

	@Override
	public List<Map<String, Object>> getLogs(String ip) {
		Jedis jedis = null;
		try {
			Map<String, JedisPool> jedisPools = jedisCluster.getClusterNodes();
			Iterator<Map.Entry<String, JedisPool>> entries = jedisPools.entrySet().iterator(); 
			while (entries.hasNext()) {
				Entry<String, JedisPool> entry = entries.next(); 
				if(entry.getKey().indexOf(ip) != -1){
					jedis = entry.getValue().getResource();
					long logLen = jedis.slowlogLen();
					List<Slowlog> logList = jedis.slowlogGet(logLen);
					jedis.close();
					List<Map<String, Object>> opList = null;
					Map<String, Object> op  = null;
					boolean flag = false;
					if (logList != null && !logList.isEmpty()) {
						opList = new LinkedList<>();
						for (Slowlog sl : logList) {
							String args = JSONUtil.toJsonStr(sl.getArgs());
							if (args.equals("[\"PING\"]") || args.equals("[\"SLOWLOG\",\"get\"]") || args.equals("[\"DBSIZE\"]") || args.equals("[\"INFO\"]")) {
								continue;
							}	
							op = new HashMap<>();
							flag = true;
							op.put("id", sl.getId());
							op.put("executeTime", ToolUtil.getDateStr(sl.getTimeStamp() * 1000));
							op.put("usedTime", sl.getExecutionTime()/1000.0 + "ms");
							op.put("args", args);
							opList.add(op);
						}
					} 
					if (flag) 
						return opList;
					else 
						return null;
				}
			}
		} finally {
		}
		return null;
	}

	@Override
	public String logEmpty(String ip) {
		return null;
	}

	@Override
	public Map<String, Object> dbSize(String ip) {
		Jedis jedis = null;
		try {
			Map<String, JedisPool> jedisPools = jedisCluster.getClusterNodes();
			Iterator<Map.Entry<String, JedisPool>> entries = jedisPools.entrySet().iterator(); 
			while (entries.hasNext()) {
				Entry<String, JedisPool> entry = entries.next(); 
				if(entry.getKey().indexOf(ip) != -1){
					jedis = entry.getValue().getResource();
					//配置redis服务信息
					Client client = jedis.getClient();
					client.dbSize();
					long dbSize = client.getIntegerReply();
					jedis.close();
					Map<String,Object> map = new HashMap<>();
					map.put("createTime", ToolUtil.getTimeAndToString());
					map.put("dbSize", dbSize);
					return map;
				}
			}
		} finally {
		}
		return null;
	}

}
