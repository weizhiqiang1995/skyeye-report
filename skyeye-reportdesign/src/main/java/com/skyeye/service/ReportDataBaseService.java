/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.entity.ReportDataSource;

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
public interface ReportDataBaseService {

    /**
     * 获取数据库列表信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void getReportDataBaseList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 新增数据库配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void insertReportDataBase(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id删除数据库配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void delReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id更新数据库配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void updateReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id查询数据库配置信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void getReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 获取数据库对象
     *
     * @param dataBaseId 数据库id
     * @return
     * @throws Exception
     */
    ReportDataSource getReportDataSource(String dataBaseId) throws Exception;

    void getReportDataBaseListToSelect(InputObject inputObject, OutputObject outputObject) throws Exception;
}
