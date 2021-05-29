/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: ReportConstants
 * @Description: 报表常量类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 23:17
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class ReportConstants {

    /**
     * 数据源类型
     */
    public static enum DataBaseMation {
        MYSQL("MySQL", "com.mysql.jdbc.Driver", "com.skyeye.sql.query.impl.MySqlQueryer"),
        SQLSERVER("SQLServer", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "com.skyeye.sql.query.impl.SqlServerQueryer"),
        ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver", "com.skyeye.sql.query.impl.OracleQueryer");

        private String type;
        private String driverClass;
        private String queryerClass;

        DataBaseMation(String type, String driverClass, String queryerClass) {
            this.type = type;
            this.driverClass = driverClass;
            this.queryerClass = queryerClass;
        }

        public static List<Map<String, Object>> getDataBaseMationList(){
            List<Map<String, Object>> beans = new ArrayList<>();
            for (DataBaseMation item : DataBaseMation.values()) {
                Map<String, Object> bean = new HashMap<>();
                bean.put("id", item.getType());
                bean.put("name", item.getType());
                bean.put("driverClass", item.getDriverClass());
                beans.add(bean);
            }
            return beans;
        }

        public static String getDricerClassByType(String type) {
            for (DataBaseMation bean : DataBaseMation.values()) {
                if (bean.getType().equals(type)) {
                    return bean.getDriverClass();
                }
            }
            return null;
        }

        public static String getTypeByDricerClass(String driverClass) {
            for (DataBaseMation bean : DataBaseMation.values()) {
                if (bean.getDriverClass().equals(driverClass)) {
                    return bean.getType();
                }
            }
            return null;
        }

        public static String getQueryerClassByType(String type) {
            for (DataBaseMation bean : DataBaseMation.values()) {
                if (bean.getType().equals(type)) {
                    return bean.getQueryerClass();
                }
            }
            return null;
        }

        public String getType() {
            return type;
        }

        public String getDriverClass() {
            return driverClass;
        }

        public String getQueryerClass() {
            return queryerClass;
        }
    }

    /**
     * 连接池类型
     */
    public static enum PoolMation {
        C3P0("C3P0", "c3p0", "com.skyeye.sql.dbpool.impl.C3p0DataSourcePool", "{\"initialPoolSize\":3,\"minPoolSize\":1,\"maxPoolSize\":10,\"maxStatements\":50,\"maxIdleTime\":1000,\"acquireIncrement\":3,\"acquireRetryAttempts\":30,\"idleConnectionTestPeriod\":60,\"breakAfterAcquireFailure\":false,\"testConnectionOnCheckout\":false}"),
        DBCP2("DBCP2", "dbcp2", "com.skyeye.sql.dbpool.impl.DBCP2DataSourcePool", "{\"initialSize\":3,\"maxIdle\":20,\"minIdle\":1,\"logAbandoned\":true,\"removeAbandoned\":true,\"removeAbandonedTimeout\":180,\"maxWait\":1000}"),
        DRUID("DRUID", "druid", "com.skyeye.sql.dbpool.impl.DruidDataSourcePool", "{\"initialSize\":3,\"maxActive\":20,\"minIdle\":1,\"maxWait\":60000,\"timeBetweenEvictionRunsMillis\":60000,\"minEvictableIdleTimeMillis\":300000,\"testWhileIdle\":true,\"testOnBorrow\":false,\"testOnReturn\":false,\"maxOpenPreparedStatements\":20,\"removeAbandoned\":true,\"removeAbandonedTimeout\":1800,\"logAbandoned\":true}"),
        NODBPOOL("无连接池", "noDbPool", "com.skyeye.sql.dbpool.impl.NoDataSourcePool", "{}");

        private String title;
        private String type;
        private String poolClass;
        private String options;

        PoolMation(String title, String type, String poolClass, String options) {
            this.title = title;
            this.type = type;
            this.poolClass = poolClass;
            this.options = options;
        }

        public static List<Map<String, Object>> getPoolMationList(){
            List<Map<String, Object>> beans = new ArrayList<>();
            for (PoolMation item : PoolMation.values()) {
                Map<String, Object> bean = new HashMap<>();
                bean.put("id", item.getType());
                bean.put("name", item.getTitle());
                bean.put("options", item.getOptions());
                beans.add(bean);
            }
            return beans;
        }

        public static String getPoolClassByType(String type) {
            for (PoolMation bean : PoolMation.values()) {
                if (bean.getType().equals(type)) {
                    return bean.getPoolClass();
                }
            }
            return null;
        }

        public static String getTitleByPoolClass(String poolClass) {
            for (PoolMation bean : PoolMation.values()) {
                if (bean.getPoolClass().equals(poolClass)) {
                    return bean.getTitle();
                }
            }
            return null;
        }

        public static String getTypeByPoolClass(String poolClass) {
            for (PoolMation bean : PoolMation.values()) {
                if (bean.getPoolClass().equals(poolClass)) {
                    return bean.getType();
                }
            }
            return null;
        }

        public static String getOptionsByType(String type) {
            for (PoolMation bean : PoolMation.values()) {
                if (bean.getType().equals(type)) {
                    return bean.getOptions();
                }
            }
            return null;
        }

        public String getType() {
            return type;
        }

        public String getPoolClass() {
            return poolClass;
        }

        public String getOptions() {
            return options;
        }

        public String getTitle() {
            return title;
        }
    }

}
