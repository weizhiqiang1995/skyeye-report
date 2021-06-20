/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.json.JSONUtil;
import com.gexin.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.object.PutObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.ReportImportHistoryDao;
import com.skyeye.dao.ReportImportModelDao;
import com.skyeye.dao.ReportModelDao;
import com.skyeye.entity.ReportModel;
import com.skyeye.entity.ReportModelAttr;
import com.skyeye.exception.CustomException;
import com.skyeye.service.ReportImportHistoryService;
import com.skyeye.service.ReportModelAttrService;
import com.skyeye.service.ReportModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportImportHistoryServiceImpl
 * @Description:
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 14:05
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
@Service
public class ReportImportHistoryServiceImpl implements ReportImportHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportImportHistoryServiceImpl.class);

    @Autowired
    private ReportImportHistoryDao reportImportHistoryDao;

    @Autowired
    private ReportImportModelDao reportImportModelDao;

    @Autowired
    private ReportModelService reportModelService;

    @Autowired
    private ReportModelDao reportModelDao;

    @Autowired
    private ReportModelAttrService reportModelAttrService;

    @Value("${IMAGES_PATH}")
    private String tPath;

    /**
     * 获取所有模型上传导入历史列表
     *
     * @param inputObject
     * @param outputObject
     */
    @Override
    public void queryReportImportHistoryList(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> inputParams = inputObject.getParams();
        List<Map<String, Object>> beans = reportImportHistoryDao.queryReportImportHistoryList(inputParams,
                new PageBounds(Integer.parseInt(inputParams.get("page").toString()), Integer.parseInt(inputParams.get("limit").toString())));
        PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>) beans;
        outputObject.setBeans(beans);
        outputObject.settotal(beansPageList.getPaginator().getTotalCount());
    }

    /**
     * 插入模型上传导入历史
     *
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param userId   用户id
     * @return 模型上传导入历史对象
     * @throws Exception
     */
    @Override
    public Map<String, Object> insertReportImportHistory(String fileName, Long fileSize, String userId) throws Exception {
        Map<String, Object> reportImportHistory = new HashMap<>();
        reportImportHistory.put("id", ToolUtil.getSurFaceId());
        reportImportHistory.put("fileName", fileName);
        reportImportHistory.put("fileSize", fileSize);
        reportImportHistory.put("createId", userId);
        reportImportHistory.put("createTime", ToolUtil.getTimeAndToString());
        reportImportHistoryDao.insertReportImportHistory(reportImportHistory);
        return reportImportHistory;
    }

    /**
     * 模型上传导入
     *
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @Override
    @Transactional(value = "transactionManager")
    public void importReportImportModel(InputObject inputObject, OutputObject outputObject) throws Exception {
        Map<String, Object> map = inputObject.getParams();
        String userId = inputObject.getLogParams().get("id").toString();
        // 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(PutObject.getRequest().getSession().getServletContext());
        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(PutObject.getRequest())) {
            // 将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) PutObject.getRequest();
            // 获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                // 读取基本信息
                String modelId = saveModelMation(file, userId);
                // 模型属性信息
                saveModelAttrMation(file, modelId);
            }
        }
    }

    /**
     * 解析并保存模型属性信息
     *
     * @param file 文件
     * @param modelId 模型id
     * @throws Exception
     */
    private void saveModelAttrMation(MultipartFile file, String modelId) throws Exception {
        ImportParams reportModelAttrParams = new ImportParams();
        reportModelAttrParams.setStartSheetIndex(1);
        List<ReportModelAttr> reportModelAttrList = ExcelImportUtil.importExcel(file.getInputStream(), ReportModelAttr.class, reportModelAttrParams);
        reportModelAttrList.forEach(bean -> {
            bean.setId(ToolUtil.getSurFaceId());
            bean.setModelId(modelId);
        });
        reportModelAttrService.insertReportModelAttr(JSONUtil.toList(JSONUtil.parseArray(JSON.toJSONString(reportModelAttrList)), null));
    }

    /**
     * 解析并保存模型信息
     *
     * @param file 文件
     * @param userId 用户id
     * @return 模型id
     * @throws Exception
     */
    private String saveModelMation(MultipartFile file, String userId) throws Exception {
        ImportParams reportModelParams = new ImportParams();
        reportModelParams.setStartSheetIndex(0);
        List<ReportModel> reportModelList = ExcelImportUtil.importExcel(file.getInputStream(), ReportModel.class, reportModelParams);
        if (reportModelList != null && !reportModelList.isEmpty()) {
            ReportModel reportModel = reportModelList.get(0);
            // 设置logo图片
            String logoPath = getSaveLogoPath();
            ToolUtil.NIOCopyFile(reportModel.getLogoPath(), tPath + logoPath);
            ToolUtil.deleteFile(reportModel.getLogoPath());
            reportModel.setLogoPath("/images" + logoPath);
            String modelId = ToolUtil.getSurFaceId();
            reportModel.setId(modelId);
            // 插入模型上传导入历史
            String historyId = insertReportImportHistory(file.getOriginalFilename(), file.getSize(), userId).get("id").toString();
            reportModel.setHistoryId(historyId);
            setSoftwareVersion(reportModel, file.getOriginalFilename());
            reportModelDao.insertReportModel(JSONUtil.toBean(JSON.toJSONString(reportModel), null));
            return modelId;
        }
        return "";
    }

    private void setSoftwareVersion(ReportModel reportModel, String fileName) throws Exception {
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        Map<String, Object> importModel = reportImportModelDao.getReportImportModelByFileName(fileName);
        if (importModel == null) {
            throw new CustomException("暂不支持该模型的导入");
        }
        String modelCode = importModel.get("modelId").toString();
        reportModel.setModelCode(modelCode);
        Integer softwareVersion = reportModelService.queryNewMaxVersionByModelCode(modelCode);
        reportModel.setSoftwareVersion(String.valueOf(softwareVersion));
    }

    private String getSaveLogoPath() {
        String folderPath = tPath + "/upload/report/";
        File pack = new File(folderPath);
        if (!pack.isDirectory()) {
            pack.mkdirs();
        }
        return "/upload/report/" + String.valueOf(System.currentTimeMillis()) + ".png";
    }

}
