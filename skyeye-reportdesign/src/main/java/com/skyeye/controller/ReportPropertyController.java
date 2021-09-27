/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportPropertyController
 * @Description: 模型---样式属性管理控制层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:15
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportPropertyController {

    @Autowired
    private ReportPropertyService reportPropertyService;

    /**
     * 获取模型属性列表信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPropertyController/getReportPropertyList")
    @ResponseBody
    public void getReportPropertyList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPropertyService.getReportPropertyList(inputObject, outputObject);
    }

    /**
     * 新增模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportPropertyController/insertReportProperty")
    @ResponseBody
    public void insertReportProperty(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportPropertyService.insertReportProperty(inputObject, outputObject);
    }

    /**
     * 删除模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportPropertyController/delReportPropertyById")
    @ResponseBody
    public void delReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportPropertyService.delReportPropertyById(inputObject, outputObject);
    }

    /**
     * 根据Id更新模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportPropertyController/updateReportPropertyById")
    @ResponseBody
    public void updateReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportPropertyService.updateReportPropertyById(inputObject, outputObject);
    }

    /**
     * 根据Id回显模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportPropertyController/getReportPropertyByIdToEdit")
    @ResponseBody
    public void getReportPropertyByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportPropertyService.getReportPropertyByIdToEdit(inputObject, outputObject);
    }

    /**
     * 根据id获取模型属性详情
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportPropertyController/getReportPropertyById")
    @ResponseBody
    public void getReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportPropertyService.getReportPropertyById(inputObject, outputObject);
    }

    /**
     * 获取所有模型属性供其他功能选择
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportPropertyController/getReportPropertyListToChoose")
    @ResponseBody
    public void getReportPropertyListToChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportPropertyService.getReportPropertyListToChoose(inputObject, outputObject);
    }
}
