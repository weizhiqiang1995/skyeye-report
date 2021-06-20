/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportModelAttrService
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 15:22
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportModelAttrService {

    /**
     * 插入模型属性信息
     *
     * @param reportModelAttr 模型属性信息
     * @return 成功条数
     * @throws Exception
     */
    int insertReportModelAttr(List<Map<String, Object>> reportModelAttr) throws Exception;

    /**
     * 根据模型id获取模型属性
     *
     * @param modelId 模型id
     * @return 模型属性
     * @throws Exception
     */
    List<Map<String, Object>> getReportModelAttrByModelId(@Param("modelId") String modelId) throws Exception;

}
