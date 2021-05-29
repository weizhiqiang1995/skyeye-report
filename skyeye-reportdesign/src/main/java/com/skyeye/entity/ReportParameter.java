/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @ClassName: ReportParameter
 * @Description: 报表参数类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:12
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Data
public class ReportParameter implements Serializable {

    /**
     * 获取报表唯一id
     */
    private String id;

    /**
     * 获取报表名称
     */
    private String name;

    /**
     * 获取报表布局形式(1:横向;2:纵向)
     */
    private LayoutType layout;

    /**
     * 获取报表统计列或计算列布局形式 (1:横向;2:纵向)
     */
    private LayoutType statColumnLayout;

    /**
     * 获取报表SQL语句
     */
    private String sqlText;

    /**
     * 获取报表元数据列集合
     */
    private List<ReportMetaDataColumn> metaColumns;

    /**
     * 获取报表中启用的统计(含计算)列名集合。如果未设置任何列名，则在报表中启用全部统计统计(含计算)列
     */
    private Set<String> enabledStatColumns;

    /**
     * 获取是否生成rowspan（跨行)的表格,默认为true
     */
    private boolean isRowSpan = true;

    /**
     * 报表参数构造函数
     *
     * @param id                 报表唯一id
     * @param name               报表名称
     * @param layout             报表布局形式 (1:横向;2:纵向)
     * @param statColumnLayout   报表统计列或计算列布局形式 (1:横向;2:纵向)
     * @param metaColumns        报表元数据列集合
     * @param enabledStatColumns 报表中启用的统计(含计算)列名集合
     * @param isRowSpan          是否生成rowspan（跨行)的表格,默认为true
     * @param sqlText            报表sql查询语句
     */
    public ReportParameter(String id, String name, int layout, int statColumnLayout,
                           List<ReportMetaDataColumn> metaColumns, Set<String> enabledStatColumns,
                           boolean isRowSpan, String sqlText) {
        this.id = id;
        this.name = name;
        this.layout = LayoutType.valueOf(layout);
        this.statColumnLayout = LayoutType.valueOf(statColumnLayout);
        this.metaColumns = metaColumns;
        this.enabledStatColumns = enabledStatColumns;
        this.isRowSpan = isRowSpan;
        this.sqlText = sqlText;
    }

    /**
     * 设置报表布局形式(1:横向;2:纵向)
     *
     * @param layout 报表布局形式(1:横向;2:纵向)
     */
    public void setLayout(int layout) {
        this.layout = LayoutType.valueOf(layout);
    }

    /**
     * 获取报表中启用的统计(含计算)列名集合。
     * <p/>
     * 如果未设置任何列名，则在报表中启用全部统计统计(含计算)列
     *
     * @return 报表中启用的统计(含计算)列名集合
     */
    public Set<String> getEnabledStatColumns() {
        return this.enabledStatColumns == null ? new HashSet<>(0) : this.enabledStatColumns;
    }

}
