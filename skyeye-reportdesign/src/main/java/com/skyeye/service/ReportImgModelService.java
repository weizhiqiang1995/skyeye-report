/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

/**
 * @ClassName: ReportImgModelService
 * @Description: 图片模型服务接口层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportImgModelService {

    /**
     * 获取所有图片模型
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportImgModelList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 保存图片模型
     *
     * @param inputObject
     * @param outputObject
     */
    void insertReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id删除图片模型设置
     *
     * @param inputObject
     * @param outputObject
     */
    void delReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据Id更新图片模型
     *
     * @param inputObject
     * @param outputObject
     */
    void updateReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 根据id获取详情信息
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception;


    /**
     * 根据状态获取图片模型列表
     *
     * @param inputObject
     * @param outputObject
     */
    void getReportImgModelListByState(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 发布
     *
     * @param inputObject
     * @param outputObject
     */
    void publishReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 取消发布
     *
     * @param inputObject
     * @param outputObject
     */
    void unPublishReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception;

}
