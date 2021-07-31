/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/

package com.skyeye.util;

import com.gexin.fastjson.JSON;
import com.skyeye.common.util.ToolUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * @ClassName: AnalysisDataToMapUtil
 * @Description: 数据来源解析工具类
 * @author: skyeye云系列--卫志强
 * @date: 2021/6/26 10:02
 * @Copyright: 2021 https://gitee.com/doc_wei01/skyeye Inc. All rights reserved.
 * 注意：本内容仅限购买后使用.禁止私自外泄以及用于其他的商业目的
 */
public class AnalysisDataToMapUtil {

    /**
     * 获取指定key的方法
     *
     * @param keyPrefix key的前缀
     * @param jsonStr json串数据
     * @param jsonStrType json串数据类型
     * @param result 结果
     * @param needGetKeys 需要获取的key的集合
     */
    public static void getMapByJson(String keyPrefix, String jsonStr, Integer jsonStrType, Map<String, Object> result, List<String> needGetKeys){
        jsonStrType = getJsonStrType(jsonStr, jsonStrType);
        // 获取jsonStr是json对象还是json集合  1.对象  2.集合
        Integer initJsonStrType = jsonStrType == 1 ? 1 : 2;
        for(String key: needGetKeys){
            getResult(keyPrefix, jsonStr, jsonStrType, result, key, initJsonStrType);
        }
    }

    private static void getResult(String keyPrefix, String jsonStr, Integer jsonStrType, Map<String, Object> result, String targetKey, Integer initJsonStrType) {
        // 这里只加载我们要获取的key的数据
        if(!ToolUtil.isBlank(keyPrefix) && !targetKey.startsWith(keyPrefix)){
            return;
        }
        if(targetKey.equalsIgnoreCase(keyPrefix) && initJsonStrType == 1){
            result.put(targetKey, getData(jsonStr, jsonStrType));
            return;
        }
        if(jsonStrType == 1){
            // 对象
            JSONObject json = JSONObject.fromObject(jsonStr);
            json.forEach((key, value) -> {
                String newKey = getNewKeyStr(keyPrefix, String.valueOf(key));
                String newStr = String.valueOf(value);
                getResult(newKey, newStr, getJsonStrType(newStr, null), result, targetKey, initJsonStrType == 1 ? 1 : initJsonStrType);
            });
        }else if(jsonStrType == 2){
            // 集合
            JSONArray json = JSONArray.fromObject(jsonStr);
            json.forEach(bean -> {
                String newJsonStr = JSON.toJSONString(bean);
                getResult(keyPrefix, newJsonStr, 1, result, targetKey, 2);
            });
        }else if(jsonStrType == 3){
            // 字符串
            if(result.containsKey(keyPrefix)){
                List<String> value = new ArrayList((List<String>) result.get(keyPrefix));
                value.add(jsonStr);
                result.put(keyPrefix, value);
            }else {
                result.put(keyPrefix, Arrays.asList(new String[]{jsonStr}));
            }
        }else if(jsonStrType == 4){
            // 字符串数组
            JSONArray json = JSONArray.fromObject(jsonStr);
            if(result.containsKey(keyPrefix)){
                List<Object> value = new ArrayList((List<Object>) result.get(keyPrefix));
                value.add(json);
                result.put(keyPrefix, value);
            }else {
                List<Object> value = new ArrayList<>();
                value.add(json);
                result.put(keyPrefix, value);
            }
        }
    }

    private static Object getData(String jsonStr, Integer jsonStrType) {
        if(jsonStrType == 1){
            // 对象
            return JSONObject.fromObject(jsonStr);
        }else if(jsonStrType == 2){
            // 集合
            return JSONArray.fromObject(jsonStr);
        }else if(jsonStrType == 3){
            // 字符串
            return jsonStr;
        }else if(jsonStrType == 4){
            // 字符串数组
            return JSONArray.fromObject(jsonStr);
        }
        return null;
    }

    private static String getNewKeyStr(String keyPrefix, String key) {
        if(ToolUtil.isBlank(keyPrefix)){
            return key;
        }else{
            return String.format(Locale.ROOT, "%s.%s", keyPrefix, key);
        }
    }

    private static Integer getJsonStrType(String jsonStr, Integer jsonStrType) {
        if(jsonStrType == null){
            if(isJsonObject(jsonStr)){
                return 1;
            }else if(isJsonStringArray(jsonStr)){
                return 4;
            }else if(isJsonArray(jsonStr)){
                return 2;
            }else{
                return 3;
            }
        }
        return jsonStrType;
    }

    /**
     * 判断json串是否是字符串数组
     *
     * @param content json串
     * @return true：是;false：否
     */
    public static boolean isJsonStringArray(String content){
        if(isJsonArray(content)){
            JSONArray json = JSONArray.fromObject(content);
            if(json.isEmpty()){
                return true;
            }
            if(!isJsonObject(JSON.toJSONString(json.get(0)))){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否是json实体对象
     *
     * @param content
     * @return
     */
    private static boolean isJsonObject(String content){
        try {
            JSONObject.fromObject(content);
            return true;
        } catch (Exception ee){
            return false;
        }
    }

    /**
     * 判断字符串是否是json集合对象
     * @param content
     * @return
     */
    private static boolean isJsonArray(String content){
        try {
            JSONArray.fromObject(content);
            return true;
        } catch (Exception ee){
            return false;
        }
    }

}
