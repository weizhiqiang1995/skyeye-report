/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportDataFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @ClassName: ReportDataBaseController
 * @Description: 数据库操作信息
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/3 23:17
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Controller
public class ReportDataFromController {

    @Autowired
    private ReportDataFromService reportDataFromService;

    /**
     * 获取所有数据来源列表
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataFromController/getReportDataFromList")
    @ResponseBody
    public void getReportDataFromList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportDataFromService.getReportDataFromList(inputObject, outputObject);
    }

    /**
     * 新增数据来源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataFromController/insertReportDataFrom")
    @ResponseBody
    public void insertReportDataBase(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataFromService.insertReportDataFrom(inputObject, outputObject);
    }

    /**
     * 根据id删除数据来源信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataFromController/delReportDataFromById")
    @ResponseBody
    public void delReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataFromService.delReportDataFromById(inputObject, outputObject);
    }

    /**
     * 根据id更新数据来源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataFromController/updateReportDataFromById")
    @ResponseBody
    public void updateReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataFromService.updateReportDataFromById(inputObject, outputObject);
    }

    /**
     * 根据id查询数据来源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataFromController/getReportDataFromById")
    @ResponseBody
    public void getReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataFromService.getReportDataFromById(inputObject, outputObject);
    }

    /**
     * 获取所有数据来源列表
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataFromController/getReportDataFromChooseList")
    @ResponseBody
    public void getReportDataFromChooseList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportDataFromService.getReportDataFromChooseList(inputObject, outputObject);
    }
}
