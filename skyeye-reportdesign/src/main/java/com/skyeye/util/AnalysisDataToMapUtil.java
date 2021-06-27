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

    public static void getMapByJson(String keyPrefix, String jsonStr, Integer jsonStrType, Map<String, Object> result){
        jsonStrType = getJsonStrType(jsonStr, jsonStrType);
        if(jsonStrType == 1){
            // 对象
            JSONObject json = JSONObject.fromObject(jsonStr);
            json.forEach((key, value) -> {
                String newKey = getNewKeyStr(keyPrefix, String.valueOf(key));
                String newStr = String.valueOf(value);
                getMapByJson(newKey, newStr, getJsonStrType(newStr, null), result);
            });
        }else if(jsonStrType == 2){
            // 集合
            JSONArray json = JSONArray.fromObject(jsonStr);
            json.forEach(bean -> {
                String newJsonStr = JSON.toJSONString(bean);
                getMapByJson(keyPrefix, newJsonStr, 1, result);
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

    private static boolean isJsonObject(String content){
        try {
            JSONObject.fromObject(content);
            return true;
        } catch (Exception ee){
            return false;
        }
    }

    private static boolean isJsonArray(String content){
        try {
            JSONArray.fromObject(content);
            return true;
        } catch (Exception ee){
            return false;
        }
    }

    public static void main(String[] args) {
        Map<String, Object> result = new HashMap<>();
        String str = "{\"employees\":[{\"firstName\":\"Bill\",\"lastName\":\"Gates\"},{\"firstName\":\"George\",\"lastName\":\"Bush\"},{\"firstName\":\"Thomas\",\"lastName\":\"Carter\"}]}";
        getMapByJson("", str, null, result);
        System.out.println(JSON.toJSONString(result));
    }

}
