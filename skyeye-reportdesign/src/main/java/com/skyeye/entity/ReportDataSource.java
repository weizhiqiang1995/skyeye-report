/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ClassName: ReportDataSource
 * @Description: 报表数据库实体类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 23:20
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Data
public class ReportDataSource implements Serializable {

    /**
     * 获取数据源唯一标识
     */
    private String uid;

    /**
     * 获取报表引擎查询器类名(如:com.easytoolsoft.easyreport.engine.query.MySqlQueryer)
     *
     * @see com.skyeye.sql.query.Queryer
     */
    private String queryerClass;

    /**
     * 获取报表引擎查询器使用的数据源连接池类名(如:com.skyeye.sql.dbpool.impl.C3p0DataSourcePool)
     *
     * @see com.skyeye.sql.dbpool.DataSourcePoolWrapper
     */
    private String dbPoolClass;

    /**
     * 获取数据源驱动类
     */
    private String driverClass;

    /**
     * 获取数据源连接字符串(JDBC)
     */
    private String jdbcUrl;

    /**
     * 获取数据源登录用户名
     */
    private String user;

    /**
     * 获取数据源登录密码
     */
    private String password;

    /**
     * 获取数据源配置选项,如果没有配置选项则设置为默认选项
     */
    private Map<String, Object> options;

    public ReportDataSource(String uid, String driverClass, String jdbcUrl, String user,
                            String password,
                            String queryerClass, String dbPoolClass) {
        this(uid, driverClass, jdbcUrl, user, password, queryerClass, dbPoolClass, new HashMap<>(3));
    }

    public ReportDataSource(String uid, String driverClass, String jdbcUrl, String user,
                            String password,
                            String queryerClass, String dbPoolClass,
                            Map<String, Object> options) {
        this.uid = uid;
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.queryerClass = queryerClass;
        this.dbPoolClass = dbPoolClass;
        this.options = options;
    }

}
