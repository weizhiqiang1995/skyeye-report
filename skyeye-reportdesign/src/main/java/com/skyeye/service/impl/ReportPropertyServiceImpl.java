/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportPropertyDao;
import com.skyeye.dao.ReportPropertyValueDao;
import com.skyeye.service.ReportPropertyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportPropertyServiceImpl
 * @Description: 模型---样式属性管理服务层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:14
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportPropertyServiceImpl implements ReportPropertyService {

    @Autowired
    private ReportPropertyDao reportPropertyDao;

    @Autowired
    private ReportPropertyValueDao reportPropertyValueDao;

    @Override
    public void getReportPropertyList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportPropertyDao.getReportPropertyList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    @Override
    @Transactional(value = "transactionManager")
    public void insertReportProperty(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        Integer optional = Integer.valueOf(inputParams.get("optional").toString());
        // 当optional=1时, 需要解析options. 当optional=2时, defaultValue为必填
        if (checkOptionalValue(inputObject, outputObject, inputParams, optional)) {
            return;
        }
        inputParams.put("createTime", ToolUtil.getTimeAndToString());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        reportPropertyDao.insertReportProperty(inputParams);
    }

    @Override
    @Transactional(value = "transactionManager")
    public void delReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        reportPropertyDao.delReportPropertyById(id);
        reportPropertyValueDao.delReportPropertyValueByPropertyId(id);
    }

    @Override
    @Transactional(value = "transactionManager")
    public void updateReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        Integer optional = Integer.valueOf(inputParams.get("optional").toString());
        String id = inputParams.get("id").toString();
        reportPropertyValueDao.delReportPropertyValueByPropertyId(id);
        // 当optional=1时, 需要解析options. 当optional=2时, defaultValue为必填
        if (checkOptionalValue(inputObject, outputObject, inputParams, optional)) {
            return;
        }
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        reportPropertyDao.updateReportPropertyById(inputParams);
    }

    private boolean checkOptionalValue(InputObject inputObject, OutputObject outputObject, Map<String, Object> inputParams, Integer optional) throws Exception {
        if (Integer.valueOf(1).equals(optional)) {
            saveReportPropertyValueList(inputParams);
        } else {
            Object defaultValue = inputParams.get("defaultValue");
            if (defaultValue == null || StringUtils.isEmpty(defaultValue.toString())) {
                outputObject.setreturnMessage("标识属性值为不可选时, 属性默认值必填");
                return true;
            }
        }
        return false;
    }

    private void saveReportPropertyValueList(Map<String, Object> inputParams) {
        List<Map<String, Object>> propertyValueList = new ArrayList<>();
        List<Map> options = JSONUtil.toList(inputParams.get("options").toString(), Map.class);
        Map<String, Object> tempMap;
        boolean flag = false;
        for (int index = 0, len = options.size(); index < len; index++) {
            tempMap = options.get(index);
            // 存放属性表值字段
            Map<String, Object> propertyValueParams = new HashMap<>();
            propertyValueParams.put("propertyId", inputParams.get("id"));
            propertyValueParams.put("id", ToolUtil.getSurFaceId());
            propertyValueParams.put("title", tempMap.get("title"));
            propertyValueParams.put("value", tempMap.get("value"));
            Integer defaultChoose = Integer.valueOf(tempMap.get("defaultChoose").toString());

            flag = !flag ? (Integer.valueOf(1).equals(defaultChoose) ? false : true) : false;
            propertyValueParams.put("defaultChoose", flag ? 2 : 1);
            propertyValueParams.put("orderBy", index + 1);
            propertyValueList.add(propertyValueParams);
        }
        reportPropertyValueDao.insertReportPropertyValue(propertyValueList);
    }

    @Override
    public void getReportPropertyByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        Map<String, Object> reportPropertyMap = reportPropertyDao.getReportPropertyById(id);
        if (reportPropertyMap != null) {
            Integer optional = Integer.valueOf(reportPropertyMap.get("optional").toString());
            if (optional.equals(1)) {
                List<Map<String, Object>> reportPropertyValueList = reportPropertyValueDao.getReportPropertyValueByPropertyId(id);
                reportPropertyMap.put("options", JSONUtil.toJsonStr(reportPropertyValueList));
            }
        }
        outputObject.setBean(reportPropertyMap);
    }

    @Override
    public void getReportPropertyById(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        Map<String, Object> reportPropertyMap = reportPropertyDao.getReportPropertyById(id);
        outputObject.setBean(reportPropertyMap);
    }

    @Override
    public void getReportPropertyValueByPropertyId(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        List<Map<String, Object>> reportPropertyValueList = reportPropertyValueDao.getReportPropertyValueByPropertyId(id);
        outputObject.setBeans(reportPropertyValueList);
    }
}
