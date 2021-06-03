/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportDataFromDao;
import com.skyeye.service.ReportDataFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public void getReportDataFromList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportDataFromDao.getReportDataFromList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    @Override
    public void insertReportDataFrom(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        String name = inputParams.get("name").toString();
        // 校验数据源名称是否重名
        if (!isDuplicateName(name)) {
            inputParams.put("name", name);
            inputParams.put("type", inputParams.get("type"));
            inputParams.put("remark", inputParams.get("remark"));
            inputParams.put("userId", inputObject.getLogParams().get("id"));
            inputParams.put("createTime", ToolUtil.getTimeAndToString());
            reportDataFromDao.insertReportDataFrom(inputParams);
        } else {
            outputObject.setreturnMessage("该数据源名称已存在.");
        }
    }

    @Override
    public void delReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        reportDataFromDao.delReportDataFromById(inputParams.get("id").toString());
    }

    @Override
    public void updateReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String name = inputParams.get("name").toString();
        if (!isDuplicateName(name)) {
            inputParams.put("userId", inputObject.getLogParams().get("id"));
            inputParams.put("createTime", ToolUtil.getTimeAndToString());
            reportDataFromDao.updateReportDataFromById(inputParams);
        } else {
            outputObject.setreturnMessage("该数据源名称已存在.");
        }
    }

    @Override
    public void getReportDataFromById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        Map<String, Object> resultMap = reportDataFromDao.getReportDataFromById(inputParams.get("id").toString());
        outputObject.setBean(resultMap);
    }

    private boolean isDuplicateName(String name) throws Exception {
        return reportDataFromDao.getDuplicateName(name) == 0 ? false : true;
    }
}
