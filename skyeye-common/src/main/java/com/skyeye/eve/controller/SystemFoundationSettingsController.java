/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.eve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.eve.service.SystemFoundationSettingsService;

@Controller
public class SystemFoundationSettingsController {

	@Autowired
	private SystemFoundationSettingsService systemFoundationSettingsService;
	
	/**
	 * 
	     * @Title: querySystemFoundationSettingsList
	     * @Description: 获取系统基础设置
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@RequestMapping("/post/SystemFoundationSettingsController/querySystemFoundationSettingsList")
	@ResponseBody
	public void querySystemFoundationSettingsList(InputObject inputObject, OutputObject outputObject) throws Exception{
		systemFoundationSettingsService.querySystemFoundationSettingsList(inputObject, outputObject);
	}
	
	/**
	 * 
	     * @Title: editSystemFoundationSettings
	     * @Description: 编辑系统基础设置
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@RequestMapping("/post/SystemFoundationSettingsController/editSystemFoundationSettings")
	@ResponseBody
	public void editSystemFoundationSettings(InputObject inputObject, OutputObject outputObject) throws Exception{
		systemFoundationSettingsService.editSystemFoundationSettings(inputObject, outputObject);
	}
}
