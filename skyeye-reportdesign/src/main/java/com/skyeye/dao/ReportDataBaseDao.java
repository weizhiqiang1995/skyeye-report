/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportImportModelDao
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 14:56
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportDataBaseDao {

    List<Map<String, Object>> getReportDataBaseList(Map<String, Object> map, PageBounds pageBounds);

    void insertReportDataBase(Map<String, Object> map);

    void delReportDataBaseById(@Param("id") String id);

    void updateReportDataBaseById(Map<String, Object> map);

    Map<String, Object> getReportDataBaseById(@Param("id") String id);
}
