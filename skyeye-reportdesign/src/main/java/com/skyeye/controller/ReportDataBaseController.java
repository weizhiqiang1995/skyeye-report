/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @ClassName: ReportDataBaseController
 * @Description: 文件模型关系
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 23:17
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Controller
public class ReportDataBaseController {

    @Autowired
    private ReportDataBaseService reportDataBaseService;

    /**
     * 获取文件模型关系表格信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataBaseController/getReportDataBaseList")
    @ResponseBody
    public void getReportDataBaseList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportDataBaseService.getReportDataBaseList(inputObject, outputObject);
    }

    /**
     * 新增数据源配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataBaseController/insertReportDataBase")
    @ResponseBody
    public void insertReportDataBase(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataBaseService.insertReportDataBase(inputObject, outputObject);
    }

    /**
     * 根据id删除数据源配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataBaseController/delReportDataBaseById")
    @ResponseBody
    public void delReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataBaseService.delReportDataBaseById(inputObject, outputObject);
    }

    /**
     * 根据id更新数据源配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataBaseController/updateReportDataBaseById")
    @ResponseBody
    public void updateReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataBaseService.updateReportDataBaseById(inputObject, outputObject);
    }

    /**
     * 根据id查询数据源配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportDataBaseController/getReportDataBaseById")
    @ResponseBody
    public void getReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        reportDataBaseService.getReportDataBaseById(inputObject, outputObject);
    }
}
