/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service.impl;

import com.gexin.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.constants.ReportConstants;
import com.skyeye.dao.ReportDataBaseDao;
import com.skyeye.entity.ReportDataSource;
import com.skyeye.service.ReportDataBaseService;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: ReportDataBaseServiceImpl
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/5/16 23:20
 *
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Service
public class ReportDataBaseServiceImpl implements ReportDataBaseService {

    @Autowired
    private ReportDataBaseDao reportDataBaseDao;

    @Override
    public void getReportDataBaseList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportDataBaseDao.getReportDataBaseList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>)beans;
        beans.forEach(bean -> {
            String driverClass = bean.get("driverClass").toString();
            String poolClass = bean.get("poolClass").toString();
            bean.put("dataType", ReportConstants.DataBaseMation.getTypeByDricerClass(driverClass));
            bean.put("poolClass", ReportConstants.PoolMation.getTitleByPoolClass(poolClass));
        });
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    @Override
    public void insertReportDataBase(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("createTime", ToolUtil.getTimeAndToString());
        setCommonDataMation(inputParams);
        reportDataBaseDao.insertReportDataBase(inputParams);
    }

    private void setCommonDataMation(Map<String, Object> inputParams){
        String dataBaseType = inputParams.get("dataType").toString();
        String poolClass = inputParams.get("poolClass").toString();
        inputParams.put("driverClass", ReportConstants.DataBaseMation.getDricerClassByType(dataBaseType));
        inputParams.put("queryerClass", ReportConstants.DataBaseMation.getQueryerClassByType(dataBaseType));
        inputParams.put("poolClass", ReportConstants.PoolMation.getPoolClassByType(poolClass));
    }

    @Override
    public void delReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        reportDataBaseDao.delReportDataBaseById(String.valueOf(inputParams.get("id")));
    }

    @Override
    public void updateReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("updateTime", ToolUtil.getTimeAndToString());
        setCommonDataMation(inputParams);
        reportDataBaseDao.updateReportDataBaseById(inputParams);
    }

    @Override
    public void getReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String id = inputParams.get("id").toString();
        Map<String, Object> bean = reportDataBaseDao.getReportDataBaseById(id);

        String driverClass = bean.get("driverClass").toString();
        String poolClass = bean.get("poolClass").toString();
        bean.put("dataType", ReportConstants.DataBaseMation.getTypeByDricerClass(driverClass));
        bean.put("poolClass", ReportConstants.PoolMation.getTypeByPoolClass(poolClass));
        outputObject.setBean(bean);
        outputObject.settotal(1);
    }

    /**
     * 获取数据库对象
     *
     * @param dataBaseId 数据库id
     * @return
     * @throws Exception
     */
    @Override
    public ReportDataSource getReportDataSource(String dataBaseId) throws Exception {
        // 获取数据源信息
        Map<String, Object> dataBase = reportDataBaseDao.getReportDataBaseById(dataBaseId);
        Map<String, Object> options = new HashMap<>();
        String optionsStr = dataBase.get("options").toString();
        if (StringUtils.isNotEmpty(optionsStr)) {
            List<Map<String, Object>> optionsList = JSONArray.fromObject(optionsStr);
            optionsList.stream().forEach(bean -> {
                options.put(bean.get("configKey").toString(), bean.get("configValue").toString());
            });
        }
        return new ReportDataSource(
                dataBaseId,
                dataBase.get("driverClass").toString(),
                dataBase.get("jdbcUrl").toString(), dataBase.get("user").toString(), dataBase.get("password").toString(),
                dataBase.get("queryerClass").toString(),
                dataBase.get("poolClass").toString(),
                options);
    }

    /**
     * 获取数据库列表信息用于选择操作
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void getReportDataBaseListToSelect(InputObject inputObject, OutputObject outputObject) throws Exception {
        List<Map<String, Object>> beans = reportDataBaseDao.getReportDataBaseListToSelect();
        outputObject.setBeans(beans);
        outputObject.settotal(beans.size());
    }
}
