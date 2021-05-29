/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.sql.dbpool.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.skyeye.entity.ReportDataSource;
import com.skyeye.sql.dbpool.DataSourcePoolWrapper;
import org.apache.commons.collections4.MapUtils;

import javax.sql.DataSource;

/**
 *
 * @ClassName: DruidDataSourcePool
 * @Description: Druid数据源连接池包装类--参考https://github.com/alibaba/druid/wiki
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:08
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class DruidDataSourcePool implements DataSourcePoolWrapper {

    @Override
    public DataSource wrap(ReportDataSource rptDs) {
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(rptDs.getDriverClass());
            dataSource.setUrl(rptDs.getJdbcUrl());
            dataSource.setUsername(rptDs.getUser());
            dataSource.setPassword(rptDs.getPassword());
            dataSource.setInitialSize(MapUtils.getInteger(rptDs.getOptions(), "initialSize", 3));
            dataSource.setMaxActive(MapUtils.getInteger(rptDs.getOptions(), "maxActive", 20));
            dataSource.setMinIdle(MapUtils.getInteger(rptDs.getOptions(), "minIdle", 1));
            dataSource.setMaxWait(MapUtils.getInteger(rptDs.getOptions(), "maxWait", 60000));
            dataSource.setTimeBetweenEvictionRunsMillis(
                MapUtils.getInteger(rptDs.getOptions(), "timeBetweenEvictionRunsMillis", 60000));
            dataSource.setMinEvictableIdleTimeMillis(
                MapUtils.getInteger(rptDs.getOptions(), "minEvictableIdleTimeMillis", 300000));
            dataSource.setTestWhileIdle(MapUtils.getBoolean(rptDs.getOptions(), "testWhileIdle", true));
            dataSource.setTestOnBorrow(MapUtils.getBoolean(rptDs.getOptions(), "testOnBorrow", false));
            dataSource.setTestOnReturn(MapUtils.getBoolean(rptDs.getOptions(), "testOnReturn", false));
            dataSource.setMaxOpenPreparedStatements(
                MapUtils.getInteger(rptDs.getOptions(), "maxOpenPreparedStatements", 20));
            dataSource.setRemoveAbandoned(MapUtils.getBoolean(rptDs.getOptions(), "removeAbandoned", true));
            dataSource.setRemoveAbandonedTimeout(
                MapUtils.getInteger(rptDs.getOptions(), "removeAbandonedTimeout", 1800));
            dataSource.setLogAbandoned(MapUtils.getBoolean(rptDs.getOptions(), "logAbandoned", true));
            return dataSource;
        } catch (final Exception ex) {
            throw new RuntimeException("C3p0DataSourcePool Create Error", ex);
        }
    }

}
