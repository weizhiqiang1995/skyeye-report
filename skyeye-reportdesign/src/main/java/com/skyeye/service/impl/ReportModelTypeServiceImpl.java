/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportModelTypeDao;
import com.skyeye.service.ReportModelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName: ReportModelTypeServiceImpl
 * @Description: 模型分类服务层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportModelTypeServiceImpl implements ReportModelTypeService {

    @Autowired
    private ReportModelTypeDao reportModelTypeDao;

    @Override
    public void getReportModelTypeList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportModelTypeDao.getReportModelTypeList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    @Override
    public void insertReportModelType(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        // 根据父id查询父层级leavel
        Integer leavel = reportModelTypeDao.getLeavelById(inputParams.get("parentId").toString());
        if (leavel == null) {
            outputObject.setreturnMessage("该父节点不存在");
            return;
        }
        inputParams.put("leavel", ++leavel);
        inputParams.put("createTime", ToolUtil.getTimeAndToString());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        reportModelTypeDao.insertReportModelType(inputParams);
    }

    @Override
    public void delReportModelTypeById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        reportModelTypeDao.delReportModelTypeById(inputParams.get("id").toString());
    }

    @Override
    public void updateReportModelTypeById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        reportModelTypeDao.updateReportModelTypeById(inputParams);
    }

    @Override
    public void getReportModelTypeByIdToEdit(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        outputObject.setBean(reportModelTypeDao.getReportModelTypeById(id));
        outputObject.settotal(1);
    }

    @Override
    public void getReportModelTypeByParentId(InputObject inputObject, OutputObject outputObject) throws Exception {
        String parentId = inputObject.getParams().get("parentId").toString();
        List<Map<String, Object>> reportModelTypeByParentId = reportModelTypeDao.getReportModelTypeByParentId(parentId);
        outputObject.setBeans(reportModelTypeByParentId);
        outputObject.settotal(reportModelTypeByParentId.size());
    }
}
