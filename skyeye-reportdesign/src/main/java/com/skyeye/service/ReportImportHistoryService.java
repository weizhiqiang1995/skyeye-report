/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service;

import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;

import java.util.Map;

/**
 * @ClassName: ReportImportHistoryService
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 14:05
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportImportHistoryService {

    void queryReportImportHistoryList(InputObject inputObject, OutputObject outputObject) throws Exception;

    /**
     * 插入模型上传导入历史
     *
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param userId 用户id
     * @return 模型上传导入历史对象
     * @throws Exception
     */
    Map<String, Object> insertReportImportHistory(String fileName, String fileSize, String userId) throws Exception;

}
