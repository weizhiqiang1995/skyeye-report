/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

/**
 *
 * @ClassName: ReportMetaDataCell
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:19
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class ReportMetaDataCell {
    private final ReportMetaDataColumn column;
    private final String name;
    private final Object value;

    public ReportMetaDataCell(ReportMetaDataColumn column, String name, Object value) {
        this.column = column;
        this.name = name;
        this.value = value;
    }

    public ReportMetaDataColumn getColumn() {
        return this.column;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}
