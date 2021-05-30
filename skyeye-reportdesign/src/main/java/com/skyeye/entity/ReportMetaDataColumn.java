/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @ClassName: ReportMetaDataColumn
 * @Description: 报表元数据列类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:18
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Data
public class ReportMetaDataColumn implements Serializable {

    /**
     * 获取报表元数据列的顺序(从1开始)
     */
    private int ordinal;

    /**
     * 获取报表元数据列名
     */
    private String name;

    /**
     * 获取报表元数据列---数据类型名称
     */
    private String dataType;

    /**
     * 获取报表元数据计算列的计算表达式
     */
    private String expression;

    /**
     * 获取报表元数据列的格式 .String.format(format,text)
     */
    private String format;

    /**
     * 获取元数据列备注
     */
    private String comment;

    /**
     * 获取报表元数据列宽(单位:像素)
     */
    private int width;

    /**
     * 获取报表元数据列的精度（即保留多少位小数,默认浮点数为4位，百分比为2位，其他为0)
     */
    private int decimals;

    /**
     * 获取报表元数据列类型-列类型(1：布局列, 2:维度列，3:统计列, 4:计算列)
     */
    private ColumnType type = ColumnType.DIMENSION;

    /**
     * 设置列排序类型(0:默认,1：数字优先升序,2:数字优先降序,3：字符优先升序,4:字符优先降序)
     */
    private ColumnSortType sortType = ColumnSortType.DEFAULT;

    /**
     * 获取元数据列是否支持小计
     */
    private boolean isExtensions;

    /**
     * 获取元数据列是否支持条件查询
     */
    private boolean isFootings;

    /**
     * 获取元数据列是否支持百分比显示
     */
    private boolean isPercent;

    /**
     * 获取配置列是否支持可选择显示
     */
    private boolean isOptional;

    /**
     * 获取配置列中的统计列(含计算)是否支持在邮件中显示
     */
    private boolean isDisplayInMail;

    /**
     * 获取元数据列是否隐藏
     */
    private boolean isHidden;

    public ReportMetaDataColumn() {
    }

    public ReportMetaDataColumn(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public String getExpression() {
        return this.expression == null ? "" : this.expression;
    }

    public String getFormat() {
        return this.format == null ? "" : this.format;
    }

    public String getComment() {
        return this.comment == null ? "" : this.comment;
    }

    public ReportMetaDataColumn copyToNew(String name) {
        return this.copyToNew(name, this.isPercent);
    }

    public ReportMetaDataColumn copyToNew(String name, boolean isPercent) {
        ReportMetaDataColumn column = new ReportMetaDataColumn();
        column.setName(name);
        column.setPercent(isPercent);
        column.setDataType(this.dataType);
        column.setExtensions(this.isExtensions);
        column.setFootings(this.isFootings);
        column.setHidden(this.isHidden);
        column.setSortType(ColumnSortType.valueOf(this.sortType.getValue()));
        column.setType(ColumnType.valueOf(this.type.getValue()));
        column.setWidth(this.width);
        column.setDecimals(this.decimals);
        column.setOptional(this.isOptional);
        column.setDisplayInMail(this.isDisplayInMail);
        column.setComment(this.comment);
        return column;
    }
}