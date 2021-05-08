/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.jedis;


public interface JedisClientService {
	
	/**
	 * 
	     * @Title: logEmpty
	     * @Description: 清空日志
	     * @param @return
	     * @param @throws Exception    参数
	     * @return String    返回类型
	     * @throws
	 */
	public String logEmpty(String ip);

	/**
	 * 
	     * @Title: set
	     * @Description: Redis SET命令用于在Redis键中设置一些字符串值
	     * @param @param key
	     * @param @param value
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public void set(String key, String value);
	
	/**
	 * 
	    * @Title: set
	    * @Description: Redis SET命令用于在Redis键中设置一些字符串值
	    * @param @param key
	    * @param @param value
	    * @param @param seconds 过期时间，单位：秒
	    * @return void    返回类型
	    * @throws
	 */
	public void set(String key, String value, int seconds);

	/**
	 * 
	     * @Title: get
	     * @Description: 根据key去查询相应的值
	     * @param @param key
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public String get(String key);

	/**
	 * 
	     * @Title: exists
	     * @Description: 判断key在Redis缓存中是否存在
	     * @param @param key
	     * @param @return    参数
	     * @return Boolean    返回类型
	     * @throws
	 */
	public Boolean exists(String key);

	/**
	 * 
	     * @Title: expire
	     * @Description: 设置key的过期时间
	     * @param @param key
	     * @param @param seconds 单位：秒
	     * @param @return    参数
	     * @return Long    返回类型
	     * @throws
	 */
	public Boolean expire(String key, int seconds);

	/**
	 * 
	     * @Title: del
	     * @Description: 删除给定的一个 key 不存在的 key 会被忽略。
	     * @param @param key
	     * @param @param field
	     * @param @return    参数
	     * @return Long    返回类型
	     * @throws
	 */
	public Boolean del(String key);
	
	/**
	 * 
	     * @Title: getLogsLen
	     * @Description: 获取日志条数
	     * @param @return
	     * @param @throws Exception    参数
	     * @return Long    返回类型
	     * @throws
	 */
	public Long getLogsLen();

	/**
	 * 
	     * @Title: logEmpty
	     * @Description: 清空日志
	     * @param @return
	     * @param @throws Exception    参数
	     * @return String    返回类型
	     * @throws
	 */
	public String logEmpty();

	/**
	 * 
	     * @Title: dbSize
	     * @Description: 获取占用内存大小
	     * @param @return
	     * @param @throws Exception    参数
	     * @return Long    返回类型
	     * @throws
	 */
	public Long dbSize();
	
}
