/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportBgImageDao;
import com.skyeye.service.ReportBgImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportBgImageServiceImpl
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/7/3 8:35
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportBgImageServiceImpl implements ReportBgImageService {

    @Autowired
    private ReportBgImageDao reportBgImageDao;

    @Value("${IMAGES_PATH}")
    private String tPath;

    /**
     * 获取背景图片列表信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void getReportBgImageList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportBgImageDao.getReportBgImageList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>)beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    /**
     * 新增背景图片信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void insertReportBgImageMation(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        inputParams.put("id", ToolUtil.getSurFaceId());
        inputParams.put("createId", inputObject.getLogParams().get("id"));
        inputParams.put("createTime", ToolUtil.getTimeAndToString());
        reportBgImageDao.insertReportBgImageMation(inputParams);
    }

    /**
     * 删除背景图片信息
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    public void deleteReportBgImageMationById(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        String rowId = inputParams.get("id").toString();
        Map<String, Object> bgImageMation = reportBgImageDao.queryReportBgImageMationById(rowId);
        if(bgImageMation != null && !bgImageMation.isEmpty()){
            String imagePath = bgImageMation.get("imagePath").toString();
            String basePath = tPath.replace("images", "");
            ToolUtil.deleteFile(basePath + imagePath);
            reportBgImageDao.deleteReportBgImageMationById(rowId);
        }
    }
}
