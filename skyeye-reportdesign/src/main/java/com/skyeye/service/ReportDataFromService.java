/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

import java.util.Map;

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

    /**
     * 获取所有数据来源列表
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void getReportDataFromList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 新增数据来源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void insertReportDataFrom(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id删除数据来源信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void delReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id更新数据来源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void updateReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id查询数据来源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void getReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 获取所有数据来源列表
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    void getReportDataFromChooseList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据数据来源id获取该数据来源下的所有数据并组装成map
     *
     * @param fromId 数据来源id
     * @return 该数据来源下的所有数据并组装成map
     * @throws Exception
     */
    Map<String, Object> getReportDataFromMapByFromId(String fromId) throws Exception;

    /**
     * 根据数据来源信息获取要取的数据
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportDataFromDateByFromId(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据数据来源ID查询Rest接口json返回值
     *
     * @return json返回值
     */
    String getRestUrlResponseByFromId(String fromId) throws Exception;
}
