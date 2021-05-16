package com.skyeye.sql.dbpool;

import com.skyeye.entity.ReportDataSource;

import javax.sql.DataSource;

/**
 *
 * @ClassName: DataSourcePoolWrapper
 * @Description: 数据源连接包装器
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 22:53
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface DataSourcePoolWrapper {
    DataSource wrap(ReportDataSource rptDs);
}
