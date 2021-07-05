/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.sql.query.factory;

import com.skyeye.entity.ReportDataSource;
import com.skyeye.sql.query.Queryer;

import java.lang.reflect.Constructor;

/**
 *
 * @ClassName: QueryerFactory
 * @Description: 报表查询器工厂方法类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:24
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class QueryerFactory {

    public static Queryer create(ReportDataSource dataSource) {
        if (dataSource != null) {
            try {
                Class<?> clazz = Class.forName(dataSource.getQueryerClass());
                Constructor<?> constructor = clazz.getConstructor(ReportDataSource.class);
                return (Queryer)constructor.newInstance(dataSource);
            } catch (final Exception ex) {
                throw new RuntimeException("create report engine queryer error", ex);
            }
        }
        return null;
    }

}
