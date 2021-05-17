/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.entity;

/**
 *
 * @ClassName: ReportQueryParamItem
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/17 21:17
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
public class ReportQueryParamItem {

    private String name;
    private String text;
    private boolean selected;

    public ReportQueryParamItem() {
    }

    public ReportQueryParamItem(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public ReportQueryParamItem(String name, String text, boolean selected) {
        this(name, text);
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
