/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @ClassName: ReportPageController
 * @Description: 报表页面信息控制类
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/26 17:40
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Controller
public class ReportPageController {

    @Autowired
    private ReportPageService reportPageService;

    /**
     * 获取报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPageController/getReportPageList")
    @ResponseBody
    public void getReportPageList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPageService.getReportPageList(inputObject, outputObject);
    }

    /**
     * 新增报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPageController/insertReportPageMation")
    @ResponseBody
    public void insertReportPageMation(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPageService.insertReportPageMation(inputObject, outputObject);
    }

    /**
     * 获取报表页面信息用于编辑
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPageController/queryReportPageMationToEditById")
    @ResponseBody
    public void queryReportPageMationToEditById(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPageService.queryReportPageMationToEditById(inputObject, outputObject);
    }

    /**
     * 编辑报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPageController/editReportPageMationById")
    @ResponseBody
    public void editReportPageMationById(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPageService.editReportPageMationById(inputObject, outputObject);
    }

    /**
     * 删除报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPageController/deleteReportPageMationById")
    @ResponseBody
    public void deleteReportPageMationById(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPageService.deleteReportPageMationById(inputObject, outputObject);
    }

    /**
     * 获取报表页面包含的模型信息用于编辑
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPageController/queryReportPageContentMationToEditById")
    @ResponseBody
    public void queryReportPageContentMationToEditById(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPageService.queryReportPageContentMationToEditById(inputObject, outputObject);
    }

    /**
     * 编辑报表页面包含的模型信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportPageController/editReportPageContentMationById")
    @ResponseBody
    public void editReportPageContentMationById(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportPageService.editReportPageContentMationById(inputObject, outputObject);
    }

}
