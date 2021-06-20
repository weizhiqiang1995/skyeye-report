/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @ClassName: ReportModelDao
 * @Description: echarts模型数据层
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 14:57
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportModelDao {

    int insertReportModel(Map<String, Object> reportModel) throws Exception;

    /**
     * 根据模型code获取最大版本号的模型信息
     *
     * @param modelCode 模型code
     * @return 最大版本号的模型信息
     * @throws Exception
     */
    Map<String, Object> queryMaxVersionReportModelByModelCode(@Param("modelCode") String modelCode) throws Exception;

    /**
     * 根据模型id获取最大版本号的模型信息
     *
     * @param modelId 模型id
     * @return 最大版本号的模型信息
     * @throws Exception
     */
    Map<String, Object> queryMaxVersionReportModelById(@Param("modelId") String modelId) throws Exception;

}
