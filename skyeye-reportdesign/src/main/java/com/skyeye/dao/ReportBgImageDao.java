/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportBgImageDao
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/7/3 8:34
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportBgImageDao {

    List<Map<String, Object>> getReportBgImageList(Map<String, Object> inputParams, PageBounds pageBounds) throws Exception;

    void insertReportBgImageMation(Map<String, Object> inputParams) throws Exception;

    Map<String, Object> queryReportBgImageMationById(@Param("id") String id) throws Exception;

    void deleteReportBgImageMationById(@Param("id") String id) throws Exception;
}
