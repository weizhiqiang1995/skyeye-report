/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.FileUtil;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportWordModelAttrDao;
import com.skyeye.dao.ReportWordModelDao;
import com.skyeye.service.ReportPropertyService;
import com.skyeye.service.ReportWordModelService;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private ReportPropertyService reportPropertyService;

    @Value("${IMAGES_PATH}")
    private String tPath;

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
        inputParams.put("state", 1);
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
    @Transactional(value = "transactionManager")
    public void delReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String id = inputParams.get("id").toString();
        Map<String, Object> reportWordModel = reportWordModelDao.getReportWordModelById(id);
        int num = reportWordModelDao.delReportWordModelById(id);
        if (num == 0) {
            outputObject.setreturnMessage("已发布的文件模型不允许删除");
        }else{
            FileUtil.deleteFile(tPath.replace("images", "") + reportWordModel.get("logo").toString());
            reportWordModelAttrDao.delReportWordModelAttrByModelId(id);
        }
    }

    @Override
    @Transactional(value = "transactionManager")
    public void updateReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String id = inputParams.get("id").toString();
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
            List<Map<String, Object>> reportPropertyList = reportWordModelAttrDao.getReportWordModelAttrByModelId(id);
            // 轮询获取属性的默认值
            reportPropertyList.forEach(bean -> {
                reportPropertyService.getPropertyDefaultValue(bean, bean.get("id").toString(), Integer.parseInt(bean.get("optional").toString()));
            });
            reportWordModelMap.put("options", reportPropertyList);
        }
        outputObject.setBean(reportWordModelMap);
        outputObject.settotal(1);
    }

    @Override
    public void getReportWordModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        Map<String, Object> reportWordModelMap = reportWordModelDao.getReportWordModelById(id);
        if (reportWordModelMap != null) {
            List<Map<String, Object>> reportPropertyList = reportWordModelAttrDao.getReportWordModelAttrByModelId(id);
            // 轮询获取属性的默认值
            reportPropertyList.forEach(bean -> {
                reportPropertyService.getPropertyDefaultValue(bean, bean.get("id").toString(), Integer.parseInt(bean.get("optional").toString()));
            });
            reportWordModelMap.put("options", reportPropertyList);
        }
        outputObject.setBean(reportWordModelMap);
        outputObject.settotal(1);
    }

    @Override
    public void getReportWordModelListByState(InputObject inputObject, OutputObject outputObject) throws Exception {
        Integer state = Integer.parseInt(inputObject.getParams().get("state").toString());
        List<Map<String, Object>> reportWordModelList = reportWordModelDao.getReportWordModelListByState(state);
        reportWordModelList.forEach(reportWordModel -> {
            String id = reportWordModel.get("id").toString();
            List<Map<String, Object>> reportPropertyList = reportWordModelAttrDao.getReportWordModelAttrByModelId(id);
            // 轮询获取属性的默认值
            reportPropertyList.forEach(bean -> {
                reportPropertyService.getPropertyDefaultValue(bean, bean.get("id").toString(), Integer.parseInt(bean.get("optional").toString()));
            });
            reportWordModel.put("attr", resetAttr(reportPropertyList, reportWordModel.get("id").toString()));
        });
        outputObject.setBeans(reportWordModelList);
        outputObject.settotal(reportWordModelList.size());
    }

    private Map<String, Object> resetAttr(List<Map<String, Object>> reportPropertyList, String modelId) {
        Map<String, Object> attr = new HashMap<>();
        reportPropertyList.forEach(bean -> {
            Map<String, Object> item = new HashMap<>();
            item.put("editor", bean.get("editorType"));
            item.put("attrCode", bean.get("code"));
            item.put("modelId", modelId);
            item.put("edit", bean.get("editor"));
            item.put("typeName", "Style属性");
            item.put("title", bean.get("title"));
            item.put("id", bean.get("id"));
            item.put("value", bean.get("defaultValue"));
            // 是否显示在编辑框
            item.put("showToEditor", bean.get("showToEditor"));
            item.put("desc", bean.get("title"));
            item.put("editorChooseValue", JSONUtils.toJSONString(bean.get("options")));
            attr.put(bean.get("code").toString(), item);
        });
        attr.put("custom.textContent", getEditFontTextAttr(modelId));
        return attr;
    }

    private Map<String, Object> getEditFontTextAttr(String modelId){
        Map<String, Object> item = new HashMap<>();
        item.put("editor", 2);
        item.put("attrCode", "custom.textContent");
        item.put("modelId", modelId);
        item.put("edit", 1);
        item.put("typeName", "Style属性");
        item.put("title", "文字");
        item.put("id", "customEditTextContentId");
        item.put("value", "Hello, Skyeye.");
        // 是否显示在编辑框
        item.put("showToEditor", 1);
        item.put("desc", "文字");
        return item;
    }

    @Override
    @Transactional(value = "transactionManager")
    public void publishReportWordModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        updateMoeldState(inputObject, 2);
    }

    @Override
    @Transactional(value = "transactionManager")
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
