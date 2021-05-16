/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportDataBaseDao;
import com.skyeye.service.ReportDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    @Override
    public void insertReportDataBase(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("createTime", ToolUtil.getTimeAndToString());
        reportDataBaseDao.insertReportDataBase(inputParams);
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
        reportDataBaseDao.updateReportDataBaseById(inputParams);
        outputObject.setBean(inputParams);
    }

    @Override
    public void getReportDataBaseById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        Map<String, Object> beans = reportDataBaseDao.getReportDataBaseById(String.valueOf(inputParams.get("id")));
        outputObject.setBean(beans);
    }
}
