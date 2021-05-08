package com.skyeye.cache.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 本地跨模块数据缓存类
 */
public class LocalCacheMap {

    private static Logger LOGGER = LoggerFactory.getLogger(LocalCacheMap.class);

    /**
     * 本地跨模块数据缓存
     */
    private static InheritableThreadLocal<Map<String, Object>> localCacheThreadLocal = new InheritableThreadLocal<>();

    public static Object get(String key, Function<String, Object> loader){
        Object value = getLocalCache().get(key);
        if(value == null){
            LOGGER.info("get data mation from function, key is {}", key);
            value = loader.apply(key);
            if(value != null){
                setLocalCache(key, value);
            }
        }else{
            LOGGER.info("get data mation from local cache, key is {}", key);
        }
        return value;
    }

    public static Map<String, Object> getLocalCache(){
        Map<String, Object> map = localCacheThreadLocal.get();
        if(map == null){
            map = new HashMap<>();
            localCacheThreadLocal.set(map);
        }
        return map;
    }

    public static void setLocalCache(String key, Object value){
        Map<String, Object> map = localCacheThreadLocal.get();
        map.put(key, value);
    }

    public static void removeLocalCache(){
        localCacheThreadLocal.remove();
    }

}
