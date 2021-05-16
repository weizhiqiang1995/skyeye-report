/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

/**
 * @ClassName: ReportImportModelService
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/9 16:46
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportDataBaseService {

    void getReportDataBaseList(InputObject inputObject, OutputObject outputObject) throws Exception;

    void insertReportDataBase(InputObject inputObject, OutputObject outputObject) throws Exception;

    void delReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception;

    void updateReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception;

    void getReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception;
}
