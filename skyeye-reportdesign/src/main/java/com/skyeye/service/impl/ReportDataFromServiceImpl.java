/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service.impl;

import com.gexin.fastjson.JSONArray;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.constants.ReportConstants;
import com.skyeye.dao.*;
import com.skyeye.service.ReportDataFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @ClassName: ReportDataBaseServiceImpl
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/3 23:20
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Service
public class ReportDataFromServiceImpl implements ReportDataFromService {

    @Autowired
    private ReportDataFromDao reportDataFromDao;

    @Autowired
    private ReportDataFromJsonDao reportDataFromJsonDao;

    @Autowired
    private ReportDataFromJsonAnalysisDao reportDataFromJsonAnalysisDao;

    @Autowired
    private ReportDataFromXMLDao reportDataFromXMLDao;

    @Autowired
    private ReportDataFromXMLAnalysisDao reportDataFromXMLAnalysisDao;

    @Autowired
    private ReportDataFromRestDao reportDataFromRestDao;

    @Autowired
    private ReportDataFromRestAnalysisDao reportDataFromRestAnalysisDao;

    /**
     * 获取数据源列表信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void getReportDataFromList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportDataFromDao.getReportDataFromList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        beans.forEach(bean -> {
            bean.put("typeName", ReportConstants.DataFromTypeMation.getNameByType(Integer.parseInt(bean.get("type").toString())));
        });
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    /**
     * 保存不同类别的数据源信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value="transactionManager")
    public void insertReportDataFrom(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String name = inputParams.get("name").toString();
        Integer type = Integer.valueOf(inputParams.get("type").toString());
        // 校验数据源名称是否重名
        if (!isDuplicateName(name, type, null)) {
            String dataFromId = ToolUtil.getSurFaceId();
            inputParams.put("name", name);
            inputParams.put("type", type);
            inputParams.put("id", dataFromId);
            inputParams.put("userId", inputObject.getLogParams().get("id"));
            inputParams.put("createTime", ToolUtil.getTimeAndToString());
            reportDataFromDao.insertReportDataFrom(inputParams);

            // 根据type获取不同类型对应入参key
            String key = ReportConstants.DataFromTypeMation.getKeyByType(type);
            // 构造数据源-子数据
            Map<String, Object> subParams = new HashMap<>();
            String subId = ToolUtil.getSurFaceId();
            subParams.put("id", subId);
            subParams.put("fromId", dataFromId);
            subParams.put(key, inputParams.get(key).toString());

            // 构造子数据源analysis数据
            List<Map<String, Object>> subAnalysisParams = getSubAnalysisData(inputParams, type, subId);
            // 根据type入不同的表
            saveDataByType(type, subParams, subAnalysisParams);
        } else {
            outputObject.setreturnMessage("该数据源名称已存在.");
        }
    }

    // 构造子数据源analysis数据
    private List<Map<String, Object>> getSubAnalysisData(Map<String, Object> inputParams, Integer type, String subId) {
        JSONArray objects = JSONArray.parseArray(inputParams.get("analysisData").toString());
        List<Map> analysisDataList = objects.toJavaList(Map.class);
        List<Map<String, Object>> subAnalysisParams = new ArrayList<>();
        Map<String, Object> subAnalysisData;
        for (Map<String, Object> obj : analysisDataList) {
            subAnalysisData = new HashMap<>();
            subAnalysisData.put("id", ToolUtil.getSurFaceId());
            subAnalysisData.put("subId", subId);
            subAnalysisData.put("key", obj.get("key"));
            subAnalysisData.put("title", obj.get("title"));
            subAnalysisData.put("remark", obj.get("remark"));
            if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {
                subAnalysisData.put("dataType", obj.get("dataType"));
                subAnalysisData.put("dataLength", obj.get("dataLength"));
                subAnalysisData.put("dataPrecision", obj.get("dataPrecision"));
            }
            subAnalysisParams.add(subAnalysisData);
        }
        return subAnalysisParams;
    }

    private void saveDataByType(Integer type, Map<String, Object> subParams, List<Map<String, Object>> subAnalysisParams) {
        if (ReportConstants.DataFromTypeMation.XML.getType() == type) {
            reportDataFromXMLDao.insertReportDataFromXML(subParams);
            reportDataFromXMLAnalysisDao.insertSubXMLAnalysis(subAnalysisParams);
        } else if (ReportConstants.DataFromTypeMation.JSON.getType() == type) {
            reportDataFromJsonDao.insertReportDataFromJson(subParams);
            reportDataFromJsonAnalysisDao.insertSubJsonAnalysis(subAnalysisParams);
        } else if (ReportConstants.DataFromTypeMation.REST_API.getType() == type) {

        } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {

        }
    }

    /**
     * 根据id删除数据源信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value="transactionManager")
    public void delReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String fromId = inputParams.get("id").toString();
        // 根据dataFromId获取对应type
        Map<String, Object> reportDataFromMap = reportDataFromDao.getReportDataFromById(fromId);
        if (reportDataFromMap != null) {
            int type = Integer.valueOf(reportDataFromMap.get("type").toString());
            // 根据fromId获取subId
            delReportDataFromByType(fromId, type);
        }
    }

    private void delReportDataFromByType(String fromId, int type) throws Exception {
        String subId;
        if (ReportConstants.DataFromTypeMation.XML.getType() == type) {
            subId = reportDataFromXMLDao.selectXmlIdByFromId(fromId);
            reportDataFromXMLAnalysisDao.delByXmlId(subId);
            reportDataFromXMLDao.delReportDataFromXMLById(subId);
        } else if (ReportConstants.DataFromTypeMation.JSON.getType() == type) {
            subId = reportDataFromJsonDao.selectIdByFromId(fromId);
            reportDataFromJsonAnalysisDao.delByJsonId(subId);
            reportDataFromJsonDao.delReportDataFromJsonById(subId);
        } else if (ReportConstants.DataFromTypeMation.REST_API.getType() == type) {

        } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {

        }
        reportDataFromDao.delReportDataFromById(fromId);
    }

    /**
     * 根据Id更新数据源信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value="transactionManager")
    public void updateReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String name = inputParams.get("name").toString();
        String id = inputParams.get("id").toString();
        Integer type = Integer.valueOf(inputParams.get("type").toString());
        if (!isDuplicateName(name, type, id)) {
            // 根据type获取不同类型对应入参key
            String key = ReportConstants.DataFromTypeMation.getKeyByType(type);
            // 更新子信息表及analysis字段信息表
            updateSubReportDataFromByType(inputParams, id, type, key);

            inputParams.put("userId", inputObject.getLogParams().get("id"));
            inputParams.put("createTime", ToolUtil.getTimeAndToString());
            reportDataFromDao.updateReportDataFromById(inputParams);
            outputObject.setBean(initResultParams(inputParams, type, key));
        } else {
            outputObject.setreturnMessage("该数据源名称已存在.");
        }
    }

    // 构造回显数据
    private Map<String, Object> initResultParams(Map<String, Object> inputParams, Integer type, String key) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", inputParams.get("id"));
        resultMap.put("type", type);
        resultMap.put("name", inputParams.get("name"));
        resultMap.put("remark", inputParams.get("remark"));
        resultMap.put("analysisData", inputParams.get("analysisData"));
        if (ReportConstants.DataFromTypeMation.XML.getType() == type
                || ReportConstants.DataFromTypeMation.JSON.getType() == type) {
            resultMap.put(key, inputParams.get(key));
        }
        return resultMap;
    }

    private void updateSubReportDataFromByType(Map<String, Object> inputParams, String fromId, int type, String key) {
        // 构造数据源-子数据
        Map<String, Object> subParams = new HashMap<>();
        subParams.put(key, inputParams.get(key).toString());
        String subId;
        if (ReportConstants.DataFromTypeMation.XML.getType() == type) {
            subId = reportDataFromXMLDao.selectXmlIdByFromId(fromId);
            subParams.put("id", subId);
            reportDataFromXMLAnalysisDao.delByXmlId(subId);
            reportDataFromXMLDao.updateReportDataFromXMLById(subParams);
            reportDataFromXMLAnalysisDao.insertSubXMLAnalysis(getSubAnalysisData(inputParams, type, subId));
        } else if (ReportConstants.DataFromTypeMation.JSON.getType() == type) {
            subId = reportDataFromJsonDao.selectIdByFromId(fromId);
            subParams.put("id", subId);
            reportDataFromJsonAnalysisDao.delByJsonId(subId);
            reportDataFromJsonDao.updateReportDataFromJsonById(subParams);
            reportDataFromJsonAnalysisDao.insertSubJsonAnalysis(getSubAnalysisData(inputParams, type, subId));
        } else if (ReportConstants.DataFromTypeMation.REST_API.getType() == type) {

        } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {

        }
    }

    /**
     * 根据Id获取数据源信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void getReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        Map<String, Object> resultMap = reportDataFromDao.getReportDataFromById(inputParams.get("id").toString());
        outputObject.setBean(resultMap);
    }

    private boolean isDuplicateName(String name, Integer type, String id) throws Exception {
        return reportDataFromDao.getDuplicateName(name, type, id) == 0 ? false : true;
    }

    /**
     * 获取所有数据来源列表
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void getReportDataFromChooseList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportDataFromDao.getReportDataFromChooseList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        beans.forEach(bean -> {
            bean.put("typeName", ReportConstants.DataFromTypeMation.getNameByType(Integer.parseInt(bean.get("type").toString())));
        });
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }
}
