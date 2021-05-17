/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.dao.ReportCommonDao;
import com.skyeye.service.ReportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * @ClassName: ReportCommonServiceImpl
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:31
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportCommonServiceImpl implements ReportCommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportCommonServiceImpl.class);

    @Autowired
    private ReportCommonDao reportCommonDao;

    /**
     * 测试数据源
     *
     * @param inputObject
     * @param outputObject
     */
    @Override
    public void testConnection(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        String driverClass = params.get("driverClass").toString();
        String url = params.get("url").toString();
        String user = params.get("user").toString();
        String pass = params.containsKey("pass") ? params.get("pass").toString() : "";
        if(!connectionDataBase(driverClass, url, user, pass)){
            outputObject.setreturnMessage("连接失败");
        }
    }

    /**
     * 连接数据源
     *
     * @param driverClass 数据源驱动类
     * @param url 数据源连接字符串
     * @param user 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    @Override
    public boolean connectionDataBase(final String driverClass, final String url, final String user,
                                  final String password) {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (final Exception e) {
            LOGGER.warn("testConnection", e);
            return false;
        } finally {
            this.releaseConnection(conn);
        }
    }

    /**
     * 释放数据源
     *
     * @param conn
     */
    private void releaseConnection(final Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (final SQLException ex) {
                LOGGER.warn("测试数据库连接后释放资源失败", ex);
            }
        }
    }

}
