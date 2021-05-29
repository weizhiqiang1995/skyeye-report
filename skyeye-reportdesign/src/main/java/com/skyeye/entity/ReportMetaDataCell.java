/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

import lombok.Data;

import java.io.Serializable;

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
@Data
public class ReportMetaDataCell implements Serializable {
    private ReportMetaDataColumn column;
    private String name;
    private Object value;

    public ReportMetaDataCell(ReportMetaDataColumn column, String name, Object value) {
        this.column = column;
        this.name = name;
        this.value = value;
    }

}
