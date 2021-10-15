/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

/**
 * @ClassName: ReportModelTypeService
 * @Description: 模型分类服务接口层
 * @author: skyeye云系列--卫志强
 * @date: 2021/10/15 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportModelTypeService {

    /**
     * 获取所有模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportModelTypeList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 保存模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    void insertReportModelType(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id删除模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    void delReportModelTypeById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id更新模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    void updateReportModelTypeById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id回显模型分类
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportModelTypeByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据父id获取子级列表
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportModelTypeByParentId(InputObject inputObject, OutputObject outputObject) throws Exception;
}
