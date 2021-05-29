/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

/**
 *
 * @ClassName: LayoutType
 * @Description: 报表布局类型
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:12
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public enum LayoutType {
    /**
     * 横向布局
     */
    HORIZONTAL(1),

    /**
     * 纵向布局;
     */
    VERTICAL(2);

    private final int value;

    LayoutType(final int value) {
        this.value = value;
    }

    public static LayoutType valueOf(final int arg) {
        for (LayoutType item : LayoutType.values()) {
            if(item.getValue() == arg){
                return item;
            }
        }
        return HORIZONTAL;
    }

    public int getValue() {
        return this.value;
    }
}
