/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.sql.dbpool;

import com.skyeye.entity.ReportDataSource;

import javax.sql.DataSource;

/**
 *
 * @ClassName: DataSourcePoolWrapper
 * @Description: 数据源连接包装器
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 23:22
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public interface DataSourcePoolWrapper {
    DataSource wrap(ReportDataSource rptDs);
}
