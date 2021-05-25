/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportCommonController
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:31
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportCommonController {

    @Autowired
    private ReportCommonService reportCommonService;

    /**
     * 测试数据源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportCommonController/testConnection")
    @ResponseBody
    public void testConnection(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportCommonService.testConnection(inputObject, outputObject);
    }

    /**
     * 解析Xml格式文本
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportCommonController/parseXmlText")
    public void parseXmlText(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportCommonService.parseXmlText(inputObject, outputObject);
    }

}
