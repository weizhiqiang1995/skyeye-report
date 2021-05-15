/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportImportModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportImportModelController
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/9 16:46
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportImportModelController {

    @Autowired
    private ReportImportModelService reportImportModelService;

    /**
     * 获取文件模型关系表格信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportImportModelController/getReportImportModelList")
    @ResponseBody
    public void getReportImportModelList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportImportModelService.getReportImportModelList(inputObject, outputObject);
    }

    /**
     * 新增文件模型关系表格信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportImportModelController/insertReportImportModel")
    @ResponseBody
    public void insertReportImportModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImportModelService.insertReportImportModel(inputObject, outputObject);
    }

    /**
     * 根据id删除文件模型关系表格信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportImportModelController/delReportImportModelById")
    @ResponseBody
    public void delReportImportModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImportModelService.delReportImportModelById(inputObject, outputObject);
    }

    /**
     * 根据id更新文件模型关系表格中fileName, modelId信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportImportModelController/updateReportImportModelById")
    @ResponseBody
    public void updateReportImportModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImportModelService.updateReportImportModelById(inputObject, outputObject);
    }
}
