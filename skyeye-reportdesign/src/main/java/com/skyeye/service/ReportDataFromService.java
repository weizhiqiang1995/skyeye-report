/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

/**
 *
 * @ClassName: ReportDataBaseService
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 23:21
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public interface ReportDataFromService {

    void getReportDataFromList(InputObject inputObject, OutputObject outputObject) throws Exception;

    void insertReportDataFrom(InputObject inputObject, OutputObject outputObject) throws Exception;

    void delReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception;

    void updateReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception;

    void getReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception;
}
