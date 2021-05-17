/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.sql.dbpool.impl;

import com.skyeye.entity.ReportDataSource;
import com.skyeye.sql.dbpool.DataSourcePoolWrapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 *
 * @ClassName: DBCP2DataSourcePool
 * @Description: c3p0数据源连接池包装类---参考http://www.mchange.com/projects/c3p0/#quickstart
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:06
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class DBCP2DataSourcePool implements DataSourcePoolWrapper {

    @Override
    public DataSource wrap(final ReportDataSource rptDs) {
        try {
            final BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(rptDs.getDriverClass());
            dataSource.setUrl(rptDs.getJdbcUrl());
            dataSource.setUsername(rptDs.getUser());
            dataSource.setPassword(rptDs.getPassword());
            dataSource.setInitialSize(MapUtils.getInteger(rptDs.getOptions(), "initialSize", 3));
            dataSource.setMaxIdle(MapUtils.getInteger(rptDs.getOptions(), "maxIdle", 20));
            dataSource.setMinIdle(MapUtils.getInteger(rptDs.getOptions(), "minIdle", 1));
            dataSource.setLogAbandoned(MapUtils.getBoolean(rptDs.getOptions(), "logAbandoned", true));
            dataSource.setRemoveAbandonedTimeout(
                MapUtils.getInteger(rptDs.getOptions(), "removeAbandonedTimeout", 180));
            dataSource.setMaxWaitMillis(MapUtils.getInteger(rptDs.getOptions(), "maxWait", 1000));
            return dataSource;
        } catch (final Exception ex) {
            throw new RuntimeException("C3p0DataSourcePool Create Error", ex);
        }
    }

}
