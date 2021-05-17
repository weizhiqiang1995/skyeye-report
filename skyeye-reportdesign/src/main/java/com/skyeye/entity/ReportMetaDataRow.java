/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: ReportMetaDataRow
 * @Description: 报表元数据行类
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:17
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class ReportMetaDataRow {
    private final Map<String, ReportMetaDataCell> cells = new HashMap<>();
    private final StringBuilder rowKeyBuilder = new StringBuilder("");

    public ReportMetaDataRow() {
    }

    public ReportMetaDataRow add(final ReportMetaDataCell cell) {
        this.cells.put(cell.getName(), cell);
        if (cell.getColumn().getType() != ColumnType.STATISTICAL) {
            final Object cellValue = cell.getValue();
            this.rowKeyBuilder.append((cellValue == null) ? "" : cellValue.toString().trim());
            this.rowKeyBuilder.append("$");
        }
        return this;
    }

    public ReportMetaDataRow addAll(final List<ReportMetaDataCell> cells) {
        cells.forEach(this::add);
        return this;
    }

    public Map<String, ReportMetaDataCell> getCells() {
        return this.cells;
    }

    public ReportMetaDataCell getCell(final String name) {
        return this.cells.get(name);
    }

    public Object getCellValue(final String name) {
        final ReportMetaDataCell cell = this.cells.get(name);
        return (cell == null) ? null : cell.getValue();
    }

    public String getRowKey() {
        return this.rowKeyBuilder.toString();
    }
}
