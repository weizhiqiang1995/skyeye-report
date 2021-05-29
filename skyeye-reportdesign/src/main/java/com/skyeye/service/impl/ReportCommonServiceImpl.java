/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.constants.ReportConstants;
import com.skyeye.dao.ReportCommonDao;
import com.skyeye.entity.ReportDataSource;
import com.skyeye.service.ReportCommonService;
import com.skyeye.service.ReportDataBaseService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

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

    @Autowired
    private ReportDataBaseService reportDataBaseService;

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
        connectionDataBase(driverClass, url, user, pass, outputObject);
    }

    /**
     * 连接数据源
     *
     * @param driverClass 数据源驱动类
     * @param url 数据源连接字符串
     * @param user 用户名
     * @param password 密码
     * @param outputObject 接口出参，如果没有可以填null
     * @return
     * @throws Exception
     */
    @Override
    public boolean connectionDataBase(final String driverClass, final String url, final String user,
                                      final String password, OutputObject outputObject) {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (final Exception e) {
            LOGGER.warn("testConnection", e);
            if(outputObject != null){
                outputObject.setreturnMessage(e.getMessage());
            }
            return false;
        } finally {
            this.releaseConnection(conn);
        }
    }

    /**
     * 解析Xml格式文本
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void parseXmlText(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        Element rootElement;
        try {
            // 获取xml文件
            Document document = DocumentHelper.parseText(inputParams.get("xmlText").toString());
            // 获取根目录
            rootElement = document.getRootElement();
        } catch (Exception ex) {
            LOGGER.info("该文本不符合xml文件格式, 故无法解析. ", ex);
            outputObject.setreturnMessage("该文本不符合xml文件格式, 故无法解析.");
            return;
        }
        Map<String, Object> resultBean = new HashMap<>();
        List<String> nodeList = new ArrayList<>();
        parseSubNode(rootElement.elements(), nodeList, rootElement.getName());
        resultBean.put("nodeArray", nodeList);
        outputObject.setBean(resultBean);
    }

    // 解析并拼接节点下所有子节点名称
    private void parseSubNode(List<DefaultElement> elements,
                              List<String> nodeList, String name) {
        elements.forEach(ele ->
                parseSubNode(ele.elements(), nodeList, name.concat(".").concat(ele.getName())));
        if (elements.size() == 0) {
            nodeList.add(name);
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

    /**
     * 获取数据源类型
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void queryDataBaseMationList(InputObject inputObject, OutputObject outputObject) throws Exception {
        List<Map<String, Object>> beans = ReportConstants.DataBaseMation.getDataBaseMationList();
        outputObject.setBeans(beans);
        outputObject.settotal(beans.size());
    }

    /**
     * 获取连接池类型
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void queryPoolMationList(InputObject inputObject, OutputObject outputObject) throws Exception {
        List<Map<String, Object>> beans = ReportConstants.PoolMation.getPoolMationList();
        outputObject.setBeans(beans);
        outputObject.settotal(beans.size());
    }

    /**
     * 解析SQL数据源
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void parseSQLText(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        String sqlText = params.get("sqlText").toString();
        String dataBaseId = params.get("dataBaseId").toString();
        // 获取数据源信息
        ReportDataSource dataBase = reportDataBaseService.getReportDataSource(dataBaseId);


    }

}
