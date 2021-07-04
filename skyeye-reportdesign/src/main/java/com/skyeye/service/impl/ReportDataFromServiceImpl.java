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
import com.skyeye.util.AnalysisDataToMapUtil;
import com.skyeye.util.XmlExercise;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDataFromServiceImpl.class);

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

    @Autowired
    private ReportDataFromSQLDao reportDataFromSQLDao;

    @Autowired
    private ReportDataFromSQLAnalysisDao reportDataFromSQLAnalysisDao;

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
            saveDataByType(inputParams, type, subParams, subAnalysisParams);
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

    private void saveDataByType(Map<String, Object> inputParams, Integer type, Map<String, Object> subParams, List<Map<String, Object>> subAnalysisParams) {
        if (ReportConstants.DataFromTypeMation.XML.getType() == type) {
            reportDataFromXMLDao.insertReportDataFromXML(subParams);
            reportDataFromXMLAnalysisDao.insertSubXMLAnalysis(subAnalysisParams);
        } else if (ReportConstants.DataFromTypeMation.JSON.getType() == type) {
            reportDataFromJsonDao.insertReportDataFromJson(subParams);
            reportDataFromJsonAnalysisDao.insertSubJsonAnalysis(subAnalysisParams);
        } else if (ReportConstants.DataFromTypeMation.REST_API.getType() == type) {
            subParams.put("method", inputParams.get("method"));
            subParams.put("header", inputParams.get("header"));
            subParams.put("requestBody", inputParams.get("requestBody"));
            reportDataFromRestDao.insertReportDataFromRest(subParams);
            reportDataFromRestAnalysisDao.insertSubRestAnalysis(subAnalysisParams);
        } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {
            subParams.put("dataBaseId", inputParams.get("sqlDataBaseId"));
            reportDataFromSQLDao.insertReportDataFromSQL(subParams);
            reportDataFromSQLAnalysisDao.insertSubSQLAnalysis(subAnalysisParams);
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
            subId = reportDataFromRestDao.selectIdByFromId(fromId);
            reportDataFromRestAnalysisDao.delByRestId(subId);
            reportDataFromRestDao.delReportDataFromRestById(subId);
        } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {
            subId = reportDataFromSQLDao.selectSqlIdByFromId(fromId);
            reportDataFromSQLDao.delReportDataFromSQLById(subId);
            reportDataFromSQLAnalysisDao.delBySqlId(subId);
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
            subId = reportDataFromRestDao.selectIdByFromId(fromId);
            subParams.put("id", subId);
            subParams.put("method", inputParams.get("method"));
            subParams.put("header", inputParams.get("header"));
            subParams.put("requestBody", inputParams.get("requestBody"));
            reportDataFromRestAnalysisDao.delByRestId(subId);
            reportDataFromRestDao.updateReportDataFromRestById(subParams);
            reportDataFromRestAnalysisDao.insertSubRestAnalysis(getSubAnalysisData(inputParams, type, subId));
        } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {
            subId = reportDataFromSQLDao.selectSqlIdByFromId(fromId);
            subParams.put("id", subId);
            reportDataFromSQLAnalysisDao.delBySqlId(subId);
            reportDataFromSQLDao.updateReportDataFromSQLById(subParams);
            reportDataFromSQLAnalysisDao.insertSubSQLAnalysis(getSubAnalysisData(inputParams, type, subId));
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
        String fromId = inputParams.get("id").toString();
        Map<String, Object> reportDataFromMap = reportDataFromDao.getReportDataFromById(fromId);

        getSubReportDataFromByType(reportDataFromMap, fromId, Integer.valueOf(reportDataFromMap.get("type").toString()));
        outputObject.setBean(reportDataFromMap);
        outputObject.settotal(1);
    }

    // 构造回显数据
    private void getSubReportDataFromByType(Map<String, Object> resultMap, String fromId, int type) throws Exception {
        String key = ReportConstants.DataFromTypeMation.getKeyByType(type);
        List<Map<String, Object>> analysisData = null;
        Map<String, Object> subReportDataFromMap;
        if (ReportConstants.DataFromTypeMation.XML.getType() == type) {
            subReportDataFromMap = reportDataFromXMLDao.selectReportDataFromXMLByFromId(fromId);
            resultMap.put(key, subReportDataFromMap.get(key));
            analysisData = reportDataFromXMLAnalysisDao.getXMLAnalysisByXmlId(subReportDataFromMap.get("id").toString());
        } else if (ReportConstants.DataFromTypeMation.JSON.getType() == type) {
            subReportDataFromMap = reportDataFromJsonDao.selectReportDataFromJsonByFromId(fromId);
            resultMap.put(key, subReportDataFromMap.get(key));
            analysisData = reportDataFromJsonAnalysisDao.getJsonAnalysisByJsonId(subReportDataFromMap.get("id").toString());
        } else if (ReportConstants.DataFromTypeMation.REST_API.getType() == type) {
            subReportDataFromMap = reportDataFromRestDao.selectReportDataFromRestByFromId(fromId);
            resultMap.put("restUrl", subReportDataFromMap.get("restUrl"));
            resultMap.put("restMethod", subReportDataFromMap.get("method"));
            resultMap.put("restHeader", subReportDataFromMap.get("header"));
            resultMap.put("restRequestBody", subReportDataFromMap.get("requestBody"));
            analysisData = reportDataFromRestAnalysisDao.getRestAnalysisByRestId(subReportDataFromMap.get("id").toString());
        } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {
            subReportDataFromMap = reportDataFromSQLDao.selectReportDataFromSQLByFromId(fromId);
            resultMap.put(key, subReportDataFromMap.get(key));
            resultMap.put("sqlDataBaseId", subReportDataFromMap.get("dataBaseId"));
            analysisData = reportDataFromSQLAnalysisDao.getSQLAnalysisBySqlId(subReportDataFromMap.get("id").toString());
        }
        resultMap.put("typeName", ReportConstants.DataFromTypeMation.getNameByType(type));
        resultMap.put("staticTplPath", ReportConstants.DataFromTypeMation.getStaticTplPathByType(type));
        resultMap.put("analysisData", analysisData);
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
            try {
                getSubReportDataFromByType(bean, bean.get("id").toString(), Integer.valueOf(bean.get("type").toString()));
            } catch (Exception ee) {
                LOGGER.warn("getReportDataFromChooseList > getSubReportDataFromByType failed.", ee);
            }
        });
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    /**
     * 根据数据来源id获取该数据来源下的所有数据并组装成map
     *
     * @param fromId 数据来源id
     * @return 该数据来源下的所有数据并组装成map
     * @throws Exception
     */
    @Override
    public Map<String, Object> getReportDataFromMapByFromId(String fromId) throws Exception {
        String jsonContent = getJsonStrByFromId(fromId);
        Map<String, Object> result = new HashMap<>();
        AnalysisDataToMapUtil.getMapByJson("", jsonContent, null, result);
        return result;
    }

    /**
     * 根据数据来源id获取数据并转换成json串
     *
     * @param fromId 数据来源id
     * @return 获取数据并转换成json串
     */
    private String getJsonStrByFromId(String fromId) throws Exception {
        // 根据dataFromId获取对应type
        Map<String, Object> reportDataFromMap = reportDataFromDao.getReportDataFromById(fromId);
        if (reportDataFromMap != null) {
            int type = Integer.valueOf(reportDataFromMap.get("type").toString());
            if (ReportConstants.DataFromTypeMation.XML.getType() == type) {
                Map<String, Object> subReportDataFromMap = reportDataFromXMLDao.selectReportDataFromXMLByFromId(fromId);
                return XmlExercise.xml2json(subReportDataFromMap.get("xmlContent").toString());
            } else if (ReportConstants.DataFromTypeMation.JSON.getType() == type) {
                Map<String, Object> subReportDataFromMap = reportDataFromJsonDao.selectReportDataFromJsonByFromId(fromId);
                return subReportDataFromMap.get("jsonContent").toString();
            } else if (ReportConstants.DataFromTypeMation.REST_API.getType() == type) {

            } else if (ReportConstants.DataFromTypeMation.SQL.getType() == type) {

            }
        }
        return "{}";
    }

    /**
     * 根据数据来源信息获取要取的数据
     *
     * @param inputObject
     * @param outputObject
     */
    @Override
    public void getReportDataFromDateByFromId(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        // 根据数据来源id获取解析对应的数据
        String fromId = params.get("fromId").toString();
        Map<String, Object> data = getReportDataFromMapByFromId(fromId);
        // 前台需要获取的数据json
        Map<String, Object> needGetData = JSONObject.fromObject(params.get("needGetDataStr").toString());
        Map<String, Object> result = new HashMap<>();
        needGetData.forEach((key, value) -> {
            if(data.containsKey(key)){
                result.put(key, data.get(key));
            }else{
                result.put(key, value);
            }
        });
        outputObject.setBean(result);
    }

}
