/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportModelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportModelTypeController
 * @Description: 模型分类控制层
 * @author: skyeye云系列--卫志强
 * @date: 2021/10/15 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportModelTypeController {

    @Autowired
    private ReportModelTypeService reportModelTypeService;

    /**
     * 获取所有模型分类
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportModelTypeController/getReportModelTypeList")
    @ResponseBody
    public void getReportModelTypeList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportModelTypeService.getReportModelTypeList(inputObject, outputObject);
    }

    /**
     * 保存模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportModelTypeController/insertReportModelType")
    @ResponseBody
    public void insertReportModelType(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportModelTypeService.insertReportModelType(inputObject, outputObject);
    }

    /**
     * 根据Id删除模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportModelTypeController/delReportModelTypeById")
    @ResponseBody
    public void delReportModelTypeById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportModelTypeService.delReportModelTypeById(inputObject, outputObject);
    }

    /**
     * 根据Id更新模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportModelTypeController/updateReportModelTypeById")
    @ResponseBody
    public void updateReportModelTypeById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportModelTypeService.updateReportModelTypeById(inputObject, outputObject);
    }

    /**
     * 根据Id回显模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportModelTypeController/getReportModelTypeByIdToEdit")
    @ResponseBody
    public void getReportModelTypeByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportModelTypeService.getReportModelTypeByIdToEdit(inputObject, outputObject);
    }

    /**
     * 根据父id获取子级列表
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportModelTypeController/getReportModelTypeByParentId")
    @ResponseBody
    public void getReportModelTypeByParentId(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportModelTypeService.getReportModelTypeByParentId(inputObject, outputObject);
    }
}
