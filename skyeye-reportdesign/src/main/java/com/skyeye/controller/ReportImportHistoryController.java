/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.controller;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.service.ReportImportHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ReportImportHistoryController
 * @Description: 模型上传导入历史控制层
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 14:04
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Controller
public class ReportImportHistoryController {

    @Autowired
    private ReportImportHistoryService reportImportHistoryService;

    /**
     * 获取所有模型上传导入历史列表
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportImportHistoryController/queryReportImportHistoryList")
    @ResponseBody
    public void queryReportImportHistoryList(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportImportHistoryService.queryReportImportHistoryList(inputObject, outputObject);
    }

    /**
     * 模型上传导入
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("/post/ReportImportHistoryController/importReportImportModel")
    @ResponseBody
    public void importReportImportModel(InputObject inputObject, OutputObject outputObject) throws Exception{
        reportImportHistoryService.importReportImportModel(inputObject, outputObject);
    }

}
