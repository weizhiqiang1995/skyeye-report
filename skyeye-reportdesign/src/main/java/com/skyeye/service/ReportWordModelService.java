/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

/**
 * @ClassName: ReportWordModelService
 * @Description: 文字模型管理服务接口层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportWordModelService {

    /**
     * 获取所有文字模型
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportWordModelList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 保存文字模型
     *
     * @param inputObject
     * @param outputObject
     */
    void insertReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id删除文字模型设置
     *
     * @param inputObject
     * @param outputObject
     */
    void delReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id更新文字模型
     *
     * @param inputObject
     * @param outputObject
     */
    void updateReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id回显模型属性
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportWordModelByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id获取详情信息
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception;


    /**
     * 根据状态获取文字模型列表
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportWordModelListByState(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 发布
     *
     * @param inputObject
     * @param outputObject
     */
    void publishReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 取消发布
     *
     * @param inputObject
     * @param outputObject
     */
    void unPublishReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception;

}
