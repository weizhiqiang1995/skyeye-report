/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ReportModelAttr
 * @Description: 模型属性
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 16:22
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Data
@ExcelTarget("ReportModelAttr")
public class ReportModelAttr implements Serializable {

    private String id;

    private String modelId;

    @Excel(name = "属性id", width = 10, isImportField = "true_st", orderNum = "1")
    private String attrCode;

    @Excel(name = "属性分类", width = 10, isImportField = "true_st", orderNum = "2")
    private String typeName;

    @Excel(name = "属性名称", width = 10, isImportField = "true_st", orderNum = "3")
    private String name;

    @Excel(name = "介绍", width = 10, isImportField = "true_st", orderNum = "4")
    private String remark;

    @Excel(name = "默认值", width = 10, isImportField = "true_st", orderNum = "5")
    private String defaultValue;

    @Excel(name = "是否可编辑", width = 10, isImportField = "true_st", replace = {"是_1", "否_2"}, orderNum = "6")
    private String edit;

    @Excel(name = "编辑器", width = 10, isImportField = "true_st", replace = {"单选框_1", "输入框_2", "颜色选择器_3",
            "数字输入框_4", "多行颜色选择器_5", "下拉框_6", "多选框_7", "滑块_8", "动态数据_9",
            "只读的输入框_98", "数据源选择_99"}, orderNum = "7")
    private String editorType;

    @Excel(name = "可选值", width = 10, isImportField = "true_st", orderNum = "8")
    private String optionalValue;

}
