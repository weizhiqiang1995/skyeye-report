/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye-report
 ******************************************************************************/

package com.skyeye.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportPageDao;
import com.skyeye.service.ReportPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: ReportPageServiceImpl
 * @Description: 报表页面信息服务类
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/26 17:44
 *   
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye-report Inc. All rights reserved.
 * 注意：本内容具体规则请参照readme执行，地址：https://gitee.com/doc_wei01/skyeye-report/blob/master/README.md
 */
@Service
public class ReportPageServiceImpl implements ReportPageService {

    @Autowired
    private ReportPageDao reportPageDao;

    /**
     * 获取报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void getReportPageList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportPageDao.getReportPageList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>)beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    /**
     * 新增报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value = "transactionManager")
    public void insertReportPageMation(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        params.put("id", ToolUtil.getSurFaceId());
        params.put("createId", inputObject.getLogParams().get("id"));
        params.put("createTime", ToolUtil.getTimeAndToString());
        reportPageDao.insertReportPageMation(params);
    }

    /**
     * 获取报表页面信息用于编辑
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void queryReportPageMationToEditById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        String id = params.get("id").toString();
        Map<String, Object> bean = reportPageDao.queryReportPageMationById(id);
        outputObject.setBean(bean);
        outputObject.settotal(1);
    }

    /**
     * 编辑报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value = "transactionManager")
    public void editReportPageMationById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        params.put("lastUpdateId", inputObject.getLogParams().get("id"));
        params.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        reportPageDao.editReportPageMationById(params);
    }

    /**
     * 删除报表页面信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value = "transactionManager")
    public void deleteReportPageMationById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        String id = params.get("id").toString();
        reportPageDao.deleteReportPageMationById(id);
    }

    /**
     * 获取报表页面包含的模型信息用于编辑
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void queryReportPageContentMationToEditById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        String id = params.get("id").toString();
        Map<String, Object> bean = reportPageDao.queryReportPageContentMationToEditById(id);
        outputObject.setBean(bean);
        outputObject.settotal(1);
    }

    /**
     * 编辑报表页面包含的模型信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value = "transactionManager")
    public void editReportPageContentMationById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> params = inputObject.getParams();
        params.put("lastUpdateId", inputObject.getLogParams().get("id"));
        params.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        reportPageDao.editReportPageContentMationById(params);
    }

}
