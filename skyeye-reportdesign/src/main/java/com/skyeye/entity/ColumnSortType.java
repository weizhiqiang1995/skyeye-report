/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

import com.skyeye.constants.ReportConstants;

/**
 *
 * @ClassName: ColumnSortType
 * @Description: 报表列排序类型
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:15
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public enum ColumnSortType {

    /**
     * 默认
     */
    DEFAULT(0),

    /**
     * 数字优先升序
     */
    DIGIT_ASCENDING(1),

    /**
     * 数字优先降序
     */
    DIGIT_DESCENDING(2),

    /**
     * 字符优先升序
     */
    CHAR_ASCENDING(3),

    /**
     * 字符优先降序
     */
    CHAR_DESCENDING(4);

    private int value;

    ColumnSortType(int value) {
        this.value = value;
    }

    public static ColumnSortType valueOf(int arg) {
        for (ColumnSortType item : ColumnSortType.values()) {
            if(item.getValue() == arg){
                return item;
            }
        }
        return DEFAULT;
    }

    public int getValue() {
        return this.value;
    }
}
