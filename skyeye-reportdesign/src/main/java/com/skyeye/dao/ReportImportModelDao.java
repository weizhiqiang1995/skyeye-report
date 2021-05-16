/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: ReportImportModelDao
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 23:19
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public interface ReportImportModelDao {

    List<Map<String, Object>> getReportImportModelList(Map<String, Object> map, PageBounds pageBounds) throws Exception;

    Integer queryReportImportModelByFileName(@Param("fileName") String fileName, @Param("id") String id) throws Exception;

    Integer queryReportImportModelByModelId(@Param("modelId") String modelId, @Param("id") String id) throws Exception;

    void insertReportImportModel(Map<String, Object> map) throws Exception;

    Integer delReportImportModelById(@Param("id") String id) throws Exception;

    void updateReportImportModelById(Map<String, Object> map) throws Exception;

    Map<String, Object> getReportImportModelById(@Param("id") String id) throws Exception;
}
