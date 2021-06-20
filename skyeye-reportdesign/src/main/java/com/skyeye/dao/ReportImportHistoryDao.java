/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportImportHistoryDao
 * @Description: 模型上传导入历史数据层
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/20 14:03
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportImportHistoryDao {

    List<Map<String, Object>> queryReportImportHistoryList(Map<String, Object> inputParams, PageBounds pageBounds) throws Exception;

    int insertReportImportHistory(Map<String, Object> reportImportHistory) throws Exception;

}
