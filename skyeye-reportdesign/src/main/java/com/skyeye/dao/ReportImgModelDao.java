/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportImgModelDao
 * @Description: 图片模型数据层
 * @author: skyeye云系列--卫志强
 * @date: 2021/10/21 21:20
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportImgModelDao {

    List<Map<String, Object>> getReportImgModelList(Map<String, Object> map, PageBounds pageBounds) throws Exception;

    void insertReportImgModel(Map<String, Object> map);

    int delReportImgModelById(@Param("id") String id);

    void updateReportImgModelById(Map<String, Object> map);

    Map<String, Object> getReportImgModelById(@Param("id") String id);

    void updateReportImgModelStateById(Map<String, Object> map);

    List<Map<String, Object>> getReportImgModelListByState(@Param("state") Integer state);
}
