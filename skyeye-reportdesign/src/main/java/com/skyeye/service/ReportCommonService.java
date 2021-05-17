/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

/**
 * @ClassName: ReportCommonService
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:31
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportCommonService {

    void testConnection(InputObject inputObject, OutputObject outputObject) throws Exception;

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
    boolean connectionDataBase(final String driverClass, final String url, final String user,
                                      final String password) throws Exception;

}
