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
 * @ClassName: ReportPageDao
 * @Description: 报表页面信息数据接口层
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/26 17:41
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public interface ReportPageDao {

    List<Map<String, Object>> getReportPageList(Map<String, Object> inputParams, PageBounds pageBounds) throws Exception;

    int insertReportPageMation(Map<String, Object> params) throws Exception;

    Map<String, Object> queryReportPageMationById(@Param("id") String id) throws Exception;

    int editReportPageMationById(Map<String, Object> params) throws Exception;

    int deleteReportPageMationById(String id) throws Exception;
}
