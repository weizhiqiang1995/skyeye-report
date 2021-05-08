/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.eve.dao;

import java.util.Map;

public interface SystemFoundationSettingsDao {

	public Map<String, Object> querySystemFoundationSettingsList() throws Exception;

	public int insertSystemFoundationSettings(Map<String, Object> map) throws Exception;

	public int editSystemFoundationSettings(Map<String, Object> map) throws Exception;

}
