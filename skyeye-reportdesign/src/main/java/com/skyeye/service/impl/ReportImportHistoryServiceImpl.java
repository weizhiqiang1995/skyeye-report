/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.constants.ReportConstants;
import com.skyeye.dao.ReportImportHistoryDao;
import com.skyeye.service.ReportImportHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportImportHistoryServiceImpl
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 14:05
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportImportHistoryServiceImpl implements ReportImportHistoryService {

    @Autowired
    private ReportImportHistoryDao reportImportHistoryDao;

    /**
     * 获取所有模型上传导入历史列表
     *
     * @param inputObject
     * @param outputObject
     */
    @Override
    public void queryReportImportHistoryList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportImportHistoryDao.queryReportImportHistoryList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>)beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    /**
     * 插入模型上传导入历史
     *
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param userId   用户id
     * @return 模型上传导入历史对象
     * @throws Exception
     */
    @Override
    public Map<String, Object> insertReportImportHistory(String fileName, String fileSize, String userId) throws Exception {
        Map<String, Object> reportImportHistory = new HashMap<>();
        reportImportHistory.put("id", ToolUtil.getSurFaceId());
        reportImportHistory.put("fileName", fileName);
        reportImportHistory.put("fileSize", fileSize);
        reportImportHistory.put("createId", userId);
        reportImportHistory.put("createTime", ToolUtil.getTimeAndToString());
        reportImportHistoryDao.insertReportImportHistory(reportImportHistory);
        return reportImportHistory;
    }
}
