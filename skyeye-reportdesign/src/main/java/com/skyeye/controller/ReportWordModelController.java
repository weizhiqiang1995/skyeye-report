/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportWordModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportWordModelController
 * @Description: 文字模型管理控制层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportWordModelController {

    @Autowired
    private ReportWordModelService reportWordModelService;

    /**
     * 获取所有文字模型
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportWordModelController/getReportWordModelList")
    @ResponseBody
    public void getReportWordModelList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportWordModelService.getReportWordModelList(inputObject, outputObject);
    }

    /**
     * 保存文字模型
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/insertReportWordModel")
    @ResponseBody
    public void insertReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.insertReportWordModel(inputObject, outputObject);
    }

    /**
     * 根据Id删除文字模型设置
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/delReportWordModelById")
    @ResponseBody
    public void delReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.delReportWordModelById(inputObject, outputObject);
    }

    /**
     * 根据Id更新文字模型
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/updateReportWordModelById")
    @ResponseBody
    public void updateReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.updateReportWordModelById(inputObject, outputObject);
    }

    /**
     * 根据Id回显模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/getReportWordModelByIdToEdit")
    @ResponseBody
    public void getReportWordModelByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.getReportWordModelByIdToEdit(inputObject, outputObject);
    }

    /**
     * 根据id获取详情信息
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/getReportWordModelById")
    @ResponseBody
    public void getReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.getReportWordModelById(inputObject, outputObject);
    }

    /**
     * 根据状态获取文字模型列表
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/getReportWordModelListByState")
    @ResponseBody
    public void getReportWordModelListByState(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.getReportWordModelListByState(inputObject, outputObject);
    }

    /**
     * 发布
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/publish")
    @ResponseBody
    public void publishReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.publishReportWordModel(inputObject, outputObject);
    }

    /**
     * 取消发布
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportWordModelController/unPublish")
    @ResponseBody
    public void unPublishReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportWordModelService.unPublishReportWordModel(inputObject, outputObject);
    }
}
