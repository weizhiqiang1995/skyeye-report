/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.util;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @ClassName: HttpRequestUtil
 * @Description: http请求--该工具类只适用于报表设计-rest接口数据源
* @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:31
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public class HttpRequestUtil {

    private final static Logger LOGGER = LogManager.getLogger(HttpRequestUtil.class);

    /**
     * 发送请求获取数据
     *
     * @param requestUrl 请求路径
     * @param requestMethod 请求方式
     * @param requestBody 请求体
     * @param requestHeader 请求头
     * @return 响应结果
     * @throws IOException 异常
     */
    public static String getDataByRequest(String requestUrl, String requestMethod, Map<String, String> requestHeader, String requestBody) throws IOException {
        String result;
        BufferedReader in = null;
        DataOutputStream out = null;
        HttpURLConnection conn = getHttpConnection(requestUrl, requestMethod);
        try {
            if (!"GET".equalsIgnoreCase(requestMethod) && requestBody != null) {
                conn.setDoInput(true);
                conn.setDoOutput(true);
                if (!CollectionUtil.isEmpty(requestHeader)) {
                    requestHeader.entrySet().forEach(cs ->
                            conn.setRequestProperty(cs.getKey(), cs.getValue())
                    );
                }
                out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(requestBody);
            }
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = in.readLine()) != null) {
                sb.append(temp).append(" ");
            }
            result = sb.toString();
        } catch (Exception ex) {
            LOGGER.warn("getUrlRespose error. ", ex);
            throw new RuntimeException("getUrlRespose error.");
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return result;
    }

    /**
     * 获取HTTP请求连接
     *
     * @param requestUrl 请求路径
     * @param requestMethod 请求方式
     * @return 返回请求连接
     */
    private static HttpURLConnection getHttpConnection(String requestUrl, String requestMethod) {
        HttpURLConnection conn;
        try {
            URL uri = new URL(requestUrl);
            conn = (HttpURLConnection) uri.openConnection();
            // requestMethod: POST, PUT, DELETE, GET
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(60000); //60 secs
            conn.setReadTimeout(60000); //60 secs
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
        } catch (Exception ex) {
            LOGGER.info("connection failed", ex);
            throw new RuntimeException("connection failed.");
        }
        return conn;
    }
}