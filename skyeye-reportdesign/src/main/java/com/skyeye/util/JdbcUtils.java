/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.util;

import com.skyeye.entity.ReportDataSource;
import com.skyeye.sql.dbpool.DataSourcePoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @ClassName: JdbcUtils
 * @Description: Jdbc工具类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:16
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class JdbcUtils {
    private static Logger logger = LoggerFactory.getLogger(JdbcUtils.class);
    private static Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(100);

    public static DataSource getDataSource(ReportDataSource rptDs) {
        // 用数据源用户名,密码,jdbcUrl做为key
        String key = String.format("%s|%s|%s", rptDs.getUser(), rptDs.getPassword(), rptDs.getJdbcUrl())
            .toLowerCase();
        DataSource dataSource = dataSourceMap.get(key);
        if (dataSource == null) {
            dataSource = DataSourcePoolFactory.create(rptDs.getDbPoolClass()).wrap(rptDs);
            dataSourceMap.put(key, dataSource);
        }
        return dataSource;
    }

    public static void releaseJdbcResource(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (final SQLException ex) {
            logger.error("数据库资源释放异常", ex);
            throw new RuntimeException("数据库资源释放异常", ex);
        }
    }
}
