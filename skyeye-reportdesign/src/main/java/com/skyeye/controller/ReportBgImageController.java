/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportBgImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportBgImageController
 * @Description: 报表基础背景图片设置
 * @author: skyeye云系列--卫志强
 * @date: 2021/7/3 8:33
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportBgImageController {

    @Autowired
    private ReportBgImageService reportBgImageService;

    /**
     * 获取背景图片列表信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportBgImageController/getReportBgImageList")
    @ResponseBody
    public void getReportBgImageList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportBgImageService.getReportBgImageList(inputObject, outputObject);
    }

    /**
     * 新增背景图片信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportBgImageController/insertReportBgImageMation")
    @ResponseBody
    public void insertReportBgImageMation(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportBgImageService.insertReportBgImageMation(inputObject, outputObject);
    }

    /**
     * 删除背景图片信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportBgImageController/deleteReportBgImageMationById")
    @ResponseBody
    public void deleteReportBgImageMationById(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportBgImageService.deleteReportBgImageMationById(inputObject, outputObject);
    }

    /**
     * 获取所有背景图片列表信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportBgImageController/getAllReportBgImageList")
    @ResponseBody
    public void getAllReportBgImageList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportBgImageService.getAllReportBgImageList(inputObject, outputObject);
    }

}
