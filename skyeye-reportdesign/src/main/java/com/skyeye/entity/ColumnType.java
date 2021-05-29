/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

/**
 *
 * @ClassName: ColumnType
 * @Description: 报表列类型
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:14
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public enum ColumnType {
    /**
     * 布局列
     */
    LAYOUT(1),

    /**
     * 维度列
     */
    DIMENSION(2),

    /**
     * 统计列
     */
    STATISTICAL(3),

    /**
     * 计算列
     */
    COMPUTED(4);

    private final int value;

    ColumnType(final int value) {
        this.value = value;
    }

    public static ColumnType valueOf(final int arg) {
        for (ColumnType item : ColumnType.values()) {
            if(item.getValue() == arg){
                return item;
            }
        }
        return DIMENSION;
    }

    public int getValue() {
        return this.value;
    }
}
