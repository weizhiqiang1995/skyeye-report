/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.sql.query.impl;

import com.skyeye.entity.ReportDataSource;
import com.skyeye.entity.ReportParameter;
import com.skyeye.sql.query.AbstractQueryer;
import com.skyeye.sql.query.Queryer;

/**
 *
 * @ClassName: SqlServerQueryer
 * @Description: MS SQLServer 数据库查询器类。
 *                  在使用该查询器时,请先参考:https://msdn.microsoft.com/library/mt484311.aspx
 *                  获取sqlserver jdbc driver,然后把相关jdbc driver的jar包加入该系统的类路径下(如WEB-INF/lib)
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:26
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class SqlServerQueryer extends AbstractQueryer implements Queryer {

    public SqlServerQueryer(final ReportDataSource dataSource, final ReportParameter parameter) {
        super(dataSource, parameter);
    }

}
