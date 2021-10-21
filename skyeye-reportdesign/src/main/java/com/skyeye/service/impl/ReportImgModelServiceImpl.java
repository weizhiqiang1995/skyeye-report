/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportImgModelDao;
import com.skyeye.service.ReportImgModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName: ReportImgModelServiceImpl
 * @Description: 图片模型服务层
 * @author: skyeye云系列--卫志强
 * @date: 2021/9/5 16:21
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportImgModelServiceImpl implements ReportImgModelService {

    @Autowired
    private ReportImgModelDao reportImgModelDao;

    @Override
    public void getReportImgModelList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportImgModelDao.getReportImgModelList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    @Override
    public void insertReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        inputParams.put("createTime", ToolUtil.getTimeAndToString());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        reportImgModelDao.insertReportImgModel(inputParams);
    }

    @Override
    public void delReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        int delNum = reportImgModelDao.delReportImgModelById(inputParams.get("id").toString());
        if (delNum == 0) {
            outputObject.setreturnMessage("已发布的图片模型不允许删除");
        }
    }

    @Override
    public void updateReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String id = inputObject.getParams().get("id").toString();
        Map<String, Object> reportImgModelMap = reportImgModelDao.getReportImgModelById(id);
        if (reportImgModelMap == null) {
            outputObject.setreturnMessage("该图片模型不存在, 请刷新界面重试!");
            return;
        }
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        reportImgModelDao.updateReportImgModelById(inputParams);
    }

    @Override
    public void getReportImgModelById(InputObject inputObject, OutputObject outputObject) throws Exception {
        String id = inputObject.getParams().get("id").toString();
        outputObject.setBean(reportImgModelDao.getReportImgModelById(id));
        outputObject.settotal(1);
    }

    @Override
    public void getReportImgModelListByState(InputObject inputObject, OutputObject outputObject) throws Exception {
        Integer state = Integer.parseInt(inputObject.getParams().get("state").toString());
        List<Map<String, Object>> ReportImgModelList = reportImgModelDao.getReportImgModelListByState(state);
        outputObject.setBeans(ReportImgModelList);
        outputObject.settotal(ReportImgModelList.size());
    }

    @Override
    public void publishReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        updateImgModelState(inputObject, 2);
    }

    @Override
    public void unPublishReportImgModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        updateImgModelState(inputObject, 1);
    }

    /**
     * 更新图片模型的发布状态
     *
     * @param inputObject 入参信息
     * @param state 1: 取消发布   2: 发布
     * @throws Exception
     */
    private void updateImgModelState(InputObject inputObject, int state) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("lastUpdateTime", ToolUtil.getTimeAndToString());
        inputParams.put("userId", inputObject.getLogParams().get("id"));
        inputParams.put("state", state);
        reportImgModelDao.updateReportImgModelStateById(inputParams);
    }
}
