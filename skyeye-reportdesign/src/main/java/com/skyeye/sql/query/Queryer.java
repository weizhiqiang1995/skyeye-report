/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.sql.query;

import com.skyeye.entity.ReportMetaDataColumn;
import com.skyeye.entity.ReportMetaDataRow;
import com.skyeye.entity.ReportQueryParamItem;

import java.util.List;

/**
 *
 * @ClassName: Queryer
 * @Description: 报表查询器接口
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:20
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public interface Queryer {
    /**
     * 从sql语句中解析出报表元数据列集合
     *
     * @param sqlText sql语句
     * @return List[ReportMetaDataColumn]
     */
    List<ReportMetaDataColumn> parseMetaDataColumns(String sqlText);

    /**
     * 从sql语句中解析出报表查询参数(如下拉列表参数）的列表项集合
     *
     * @param sqlText sql语句
     * @return List[ReportQueryParamItem]
     */
    List<ReportQueryParamItem> parseQueryParamItems(String sqlText);

    /**
     * 获取报表原始数据行集合
     *
     * @param sqlText sql语句
     * @return
     */
    List<ReportMetaDataRow> getMetaDataRows(String sqlText);

}
