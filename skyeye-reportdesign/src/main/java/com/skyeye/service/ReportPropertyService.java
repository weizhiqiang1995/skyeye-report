/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

/**
 * @ClassName: ReportPropertyService
 * @Description: 模型---样式属性管理服务接口层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:14
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportPropertyService {

    /**
     * 获取模型属性列表信息
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportPropertyList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 新增模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    void insertReportProperty(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 删除模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    void delReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id更新模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    void updateReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id回显模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportPropertyByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id获取模型属性详情
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception;


    /**
     * 根据propertyid获取模型属性值列表
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportPropertyValueByPropertyId(InputObject inputObject, OutputObject outputObject) throws Exception;

}
