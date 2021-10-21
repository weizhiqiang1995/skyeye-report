/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportImgModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportImgModelController
 * @Description: 图片模型控制层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportImgModelController {

    @Autowired
    private ReportImgModelService reportImgModelService;

    /**
     * 获取所有图片模型
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportImgModelController/getReportImgModelList")
    @ResponseBody
    public void getReportImgModelList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportImgModelService.getReportImgModelList(inputObject, outputObject);
    }

    /**
     * 保存图片模型
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/insertReportImgModel")
    @ResponseBody
    public void insertReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.insertReportImgModel(inputObject, outputObject);
    }

    /**
     * 根据Id删除文字模型设置
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/delReportImgModelById")
    @ResponseBody
    public void delReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.delReportImgModelById(inputObject, outputObject);
    }

    /**
     * 根据Id更新图片模型
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/updateReportImgModelById")
    @ResponseBody
    public void updateReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.updateReportImgModelById(inputObject, outputObject);
    }

    /**
     * 根据Id回显图片模型
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/getReportImgModelByIdToEdit")
    @ResponseBody
    public void getReportImgModelByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.getReportImgModelById(inputObject, outputObject);
    }

    /**
     * 根据id获取详情信息
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/getReportImgModelById")
    @ResponseBody
    public void getReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.getReportImgModelById(inputObject, outputObject);
    }

    /**
     * 根据状态获取图片模型列表
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/getReportImgModelListByState")
    @ResponseBody
    public void getReportImgModelListByState(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.getReportImgModelListByState(inputObject, outputObject);
    }

    /**
     * 发布
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/publish")
    @ResponseBody
    public void publishReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.publishReportImgModel(inputObject, outputObject);
    }

    /**
     * 取消发布
     *
     * @param inputObject
     * @param outputObject
     */
    @RequestMapping("/post/ReportImgModelController/unPublish")
    @ResponseBody
    public void unPublishReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportImgModelService.unPublishReportImgModel(inputObject, outputObject);
    }
}
