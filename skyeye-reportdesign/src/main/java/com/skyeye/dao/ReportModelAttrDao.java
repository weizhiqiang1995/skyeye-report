/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportModelAttrDao
 * @Description: 模型属性数据层
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 15:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportModelAttrDao {

    int insertReportModelAttr(List<Map<String, Object>> reportModelAttr) throws Exception;

    /**
     * 根据模型id获取模型属性
     *
     * @param modelId 模型id
     * @return 模型属性
     * @throws Exception
     */
    List<Map<String, Object>> getReportModelAttrByModelId(@Param("modelId") String modelId) throws Exception;

    /**
     * 根据模型id获取模型属性用于编辑器
     *
     * @param modelId 模型id
     * @return 模型属性
     * @throws Exception
     */
    List<Map<String, Object>> getReportModelAttrToEditorByModelId(@Param("modelId") String modelId) throws Exception;

}
