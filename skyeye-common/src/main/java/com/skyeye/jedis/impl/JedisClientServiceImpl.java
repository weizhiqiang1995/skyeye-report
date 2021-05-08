/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.jedis.impl;

import com.skyeye.common.util.ToolUtil;
import com.skyeye.jedis.JedisClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 
     * @ClassName: JedisClientCluster
     * @Description: redis
     * @author 卫志强
     * @date 2018年11月17日
     *
 */
@Service
public class JedisClientServiceImpl implements JedisClientService {

	@Autowired
	private RedisTemplate<String,  String> redisTemplate;
	
	/**
	 * 默认key失效时间为十天
	 */
	private int TEN_DAY_SECONDS = 10 * 24 * 60 * 60;

	@Override
	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
		//为防止缓存穿透，空值设置过期时间，2S后自动删除
		if(ToolUtil.isBlank(value)){
			expire(key, 2);
		}else{
			expire(key, TEN_DAY_SECONDS);
		}
	}
	
	@Override
	public void set(String key, String value, int seconds) {
		redisTemplate.opsForValue().set(key, value);
		//为防止缓存穿透，空值设置过期时间，2S后自动删除
		if(ToolUtil.isBlank(value)){
			expire(key, 2);
		}else{
			expire(key, seconds);
		}
	}

	@Override
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public Boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Boolean expire(String key, int seconds) {
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	@Override
	public Boolean del(String key) {
        return redisTemplate.delete(key);
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
	public String logEmpty(String ip) {
		return null;
	}

}
