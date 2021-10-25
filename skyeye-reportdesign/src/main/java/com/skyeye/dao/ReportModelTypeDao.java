/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ReportModelTypeDao
 * @Description: 模型分类数据层
 * @author: skyeye云系列--卫志强
 * @date: 2021/10/15 16:20
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public interface ReportModelTypeDao {

    /**
     * 可根据name模糊查询获取所有模型分类
     *
     * @param map 查询参数
     * @param pageBounds 分页参数
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getReportModelTypeList(Map<String, Object> map, PageBounds pageBounds) throws Exception;

    /**
     * 保存模型分类
     *
     * @param map 入库参数
     */
    void insertReportModelType(Map<String, Object> map);

    /**
     * 根据id查询数据
     *
     * @param id 唯一标识
     * @return
     */
    int delReportModelTypeById(@Param("id") String id);

    /**
     * 根据id更新数据
     *
     * @param map 更新参数
     */
    void updateReportModelTypeById(Map<String, Object> map);

    /**
     * 根据id查询详情
     *
     * @param id 唯一标识
     * @return
     */
    Map<String, Object> getReportModelTypeById(@Param("id") String id);

    /**
     * 根据id查询层级leavel
     *
     * @param id 唯一标识
     * @return
     */
    Integer getLeavelById(@Param("id") String id);

    /**
     * 根据parent_id + name获取id
     *
     * @param map 查询参数
     * @return 唯一id
     */
    String getIdOnConditions(Map<String, Object> map);

    /**
     * 根据父id查询子级列表
     *
     * @param parentId 父id
     * @return
     */
    List<Map<String, Object>> getReportModelTypeByParentId(@Param("parentId") String parentId);
}
