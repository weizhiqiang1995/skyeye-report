/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportWordModelAttrDao;
import com.skyeye.dao.ReportWordModelDao;
import com.skyeye.service.ReportWordModelService;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName: ReportWordModelServiceImpl
 * @Description: 文字模型管理服务层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportWordModelServiceImpl implements ReportWordModelService {

    @Autowired
    private ReportWordModelDao reportWordModelDao;

    @Autowired
    private ReportWordModelAttrDao reportWordModelAttrDao;

    @Override
    public void getReportWordModelList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportWordModelDao.getReportWordModelList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    @Override
    @Transactional(value = "transactionManager")
    public void insertReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        Object stateObj = inputParams.get("state");
        // state默认为未发布
        Integer state = stateObj == null || StringUtil.isBlank(stateObj.toString()) ? 1 : Integer.valueOf(stateObj.toString());
        inputParams.put("state", state);
        insertReportWordModelAttr(inputParams);
        inputParams.put("createTime", ToolUtil.getTimeAndToString());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        reportWordModelDao.insertReportWordModel(inputParams);
    }

    private void insertReportWordModelAttr(Map<String, Object> inputParams) {
        String optionsStr = Optional.ofNullable(inputParams.get("options")).orElse("").toString();
        List<Map> options = !StringUtil.isBlank(optionsStr) ? JSONUtil.toList(optionsStr, Map.class) : new ArrayList<>();
        if (!CollectionUtil.isEmpty(options)) {
            List<Map<String, Object>> reportWordModelAttrList = new ArrayList<>();
            Map<String, Object> tempMap;
            for (int index = 0, len = options.size(); index < len; index++) {
                tempMap = options.get(index);
                // 存放文字模型属性表值字段
                Map<String, Object> reportWordModelAttr = new HashMap<>();
                reportWordModelAttr.put("id", ToolUtil.getSurFaceId());
                reportWordModelAttr.put("wordId", inputParams.get("id"));
                reportWordModelAttr.put("propertyId", tempMap.get("propertyId"));
                reportWordModelAttr.put("editor", tempMap.get("editor"));
                reportWordModelAttr.put("showToEditor", tempMap.get("showToEditor"));
                reportWordModelAttrList.add(reportWordModelAttr);
            }
            reportWordModelAttrDao.insertReportWordModelAttr(reportWordModelAttrList);
        }
    }

    @Override
    public void delReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String id = inputParams.get("id").toString();
        int num = reportWordModelDao.delReportWordModelById(id);
        if (num == 0) {
            outputObject.setreturnMessage("已发布的文件模型不允许删除");
        }
    }

    @Override
    @Transactional(value = "transactionManager")
    public void updateReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String id = inputParams.get("id").toString();
        Object stateObj = inputParams.get("state");
        // state默认为未发布
        Integer state = stateObj == null || StringUtil.isBlank(stateObj.toString()) ? 1 : Integer.valueOf(stateObj.toString());
        inputParams.put("state", state);
        reportWordModelAttrDao.delReportWordModelAttrByModelId(id);
        insertReportWordModelAttr(inputParams);
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        reportWordModelDao.updateReportWordModelById(inputParams);
    }

    @Override
    public void getReportWordModelByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        Map<String, Object> reportWordModelMap = reportWordModelDao.getReportWordModelById(id);
        if (reportWordModelMap != null) {
            List<Map<String, Object>> reportPropertyValueList = reportWordModelAttrDao.getReportWordModelAttrByModelId(id);
            reportWordModelMap.put("options", JSONUtil.toJsonStr(reportPropertyValueList));
        }
        outputObject.setBean(reportWordModelMap);
    }

    @Override
    public void getReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        Map<String, Object> reportPropertyMap = reportWordModelDao.getReportWordModelById(id);
        outputObject.setBean(reportPropertyMap);
    }

    @Override
    public void getReportWordModelAttrByModelId(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        List<Map<String, Object>> reportPropertyValueList = reportWordModelAttrDao.getReportWordModelAttrByModelId(id);
        outputObject.setBeans(reportPropertyValueList);
    }

    @Override
    public void publishReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        updateMoeldState(inputObject, 2);
    }

    @Override
    public void unPublishReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        updateMoeldState(inputObject, 1);
    }

    private void updateMoeldState(InputObject inputObject, int state) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("state", state);
        reportWordModelDao.updateReportWordModelStateById(inputParams);
    }
}
