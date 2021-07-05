/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.sql.query;

import com.skyeye.entity.*;
import com.skyeye.exception.CustomException;
import com.skyeye.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @ClassName: AbstractQueryer
 * @Description: 数据库查询父类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/30 0:29
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public abstract class AbstractQueryer {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ReportDataSource dataSource;

    protected AbstractQueryer(ReportDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取sql查询出来的所有列
     *
     * @param sqlText sql语句
     * @return
     */
    public List<ReportMetaDataColumn> parseMetaDataColumns(String sqlText) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            this.logger.debug("Parse Report MetaDataColumns SQL:{},", sqlText);
            // 创建连接
            conn = this.getJdbcConnection();
            // 创建通讯
            stmt = conn.createStatement();
            // 执行sql
            rs = stmt.executeQuery(this.preprocessSqlText(sqlText));
            // 获取列
            List<ReportMetaDataColumn> columns = getReportMetaDataColumns(rs);
            return columns;
        } catch (SQLException ex) {
            throw new CustomException(ex);
        } finally {
            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
        }
    }

    private List<ReportMetaDataColumn> getReportMetaDataColumns(ResultSet rs) throws SQLException {
        // 获取结果
        ResultSetMetaData rsMataData = rs.getMetaData();
        int count = rsMataData.getColumnCount();
        List<ReportMetaDataColumn> columns = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            ReportMetaDataColumn column = new ReportMetaDataColumn();
            column.setName(rsMataData.getColumnLabel(i));
            column.setDataType(rsMataData.getColumnTypeName(i));
            column.setWidth(rsMataData.getColumnDisplaySize(i));
            columns.add(column);
        }
        return columns;
    }

    public List<ReportQueryParamItem> parseQueryParamItems(String sqlText) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        HashSet<String> set = new HashSet<>();
        List<ReportQueryParamItem> rows = new ArrayList<>();

        try {
            this.logger.debug(sqlText);
            conn = this.getJdbcConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlText);
            while (rs.next()) {
                String name = rs.getString("name");
                String text = rs.getString("text");
                name = (name == null) ? "" : name.trim();
                text = (text == null) ? "" : text.trim();
                if (!set.contains(name)) {
                    set.add(name);
                }
                rows.add(new ReportQueryParamItem(name, text));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
        }
        set.clear();
        return rows;
    }

    public List<ReportMetaDataRow> getMetaDataRows(String sqlText) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 创建连接
            conn = this.getJdbcConnection();
            // 创建通讯
            stmt = conn.createStatement();
            // 执行sql
            rs = stmt.executeQuery(sqlText);
            return this.getMetaDataRows(rs, this.getSqlColumns(this.getReportMetaDataColumns(rs)));
        } catch (Exception ex) {
            logger.warn(String.format("SqlText:{}，Msg is: ", sqlText, ex));
            throw new CustomException(ex);
        } finally {
            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
        }
    }

    protected List<ReportMetaDataRow> getMetaDataRows(ResultSet rs, List<ReportMetaDataColumn> sqlColumns)
        throws SQLException {
        List<ReportMetaDataRow> rows = new ArrayList<>();

        while (rs.next()) {
            ReportMetaDataRow row = new ReportMetaDataRow();
            for (ReportMetaDataColumn column : sqlColumns) {
                Object value = rs.getObject(column.getName());
                if (column.getDataType().contains("BINARY")) {
                    value = new String((byte[])value);
                }
                row.add(new ReportMetaDataCell(column, column.getName(), value));
            }
            rows.add(row);
        }
        return rows;
    }

    protected List<ReportMetaDataColumn> getSqlColumns(List<ReportMetaDataColumn> metaDataColumns) {
        return metaDataColumns.stream()
            .filter(x -> x.getType() != ColumnType.COMPUTED)
            .collect(Collectors.toList());
    }

    /**
     * 预处理获取报表列集合的sql语句，
     * 在这里可以拦截全表查询等sql， 因为如果表的数据量很大，将会产生过多的内存消耗，甚至性能问题
     *
     * @param sqlText 原sql语句
     * @return 预处理后的sql语句
     */
    protected String preprocessSqlText(String sqlText) {
        return sqlText;
    }

    /**
     * 获取当前报表查询器的JDBC Connection对象
     *
     * @return Connection
     */
    protected Connection getJdbcConnection() {
        try {
            Class.forName(this.dataSource.getDriverClass());
            return JdbcUtils.getDataSource(this.dataSource).getConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
