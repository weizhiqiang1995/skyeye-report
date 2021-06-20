/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName: ReportModel
 * @Description: 模型
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Data
@ExcelTarget("ReportModel")
@Component
public class ReportModel implements Serializable {

    private String id;

    private String historyId;

    private String modelCode;

    @Excel(name = "名称", width = 10, isImportField = "true_st", orderNum = "1")
    private String name;

    @Excel(name = "默认宽度", width = 10, isImportField = "true_st", orderNum = "2")
    private String defaultWidth;

    @Excel(name = "默认高度", width = 10, isImportField = "true_st", orderNum = "3")
    private String defaultHeight;

    @Excel(name = "最小宽度", width = 10, isImportField = "true_st", orderNum = "4")
    private String minWidth;

    @Excel(name = "最小高度", width = 10, isImportField = "true_st", orderNum = "5")
    private String minHeight;

    @Excel(name = "默认背景色", width = 10, isImportField = "true_st", orderNum = "6")
    private String defaultBgColor;

    @Excel(name = "透明度", width = 10, isImportField = "true_st", orderNum = "7")
    private String bgTransparency;

    @Excel(name = "LOGO", type = 2, width = 10, isImportField = "true_st", imageType = 1)
    private String logoPath;

    /**
     * 版本
     */
    private String softwareVersion;

    /**
     * 状态  1.正常  2.废弃
     */
    private Integer state = 1;

}
