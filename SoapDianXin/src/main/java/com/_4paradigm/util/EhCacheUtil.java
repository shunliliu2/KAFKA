package com._4paradigm.util;

import com._4paradigm.log.CommonLogger;
import com.alibaba.fastjson.JSONArray;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.dom4j.Document;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class EhCacheUtil {
    //private static String filelo="/Users/4paradigm/Desktop/";
    private static String filelo="/opt/server/";
    private static final String PATH = filelo+"ehcache.xml";
    private CacheManager manager;
    private static EhCacheUtil ehCache;

    public static final String TAXI_CACHE = "JsonObject";
    public static final String GROUP_TREE_KEY = "groupTreeList";
    public static final String GROUP_VEHICLE_TREE_KEY = "groupVehicleTreeList";

    /**
     * 获得缓存配置管理
     *
     * @param path
     */
    public EhCacheUtil(String path) {
        try {
            manager = CacheManager.create(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化缓存管理类
     *
     * @return
     */
    public static EhCacheUtil getInstance() {
        try {
            if (ehCache == null) {
                ehCache = new EhCacheUtil(PATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ehCache;
    }

    /**
     * 获取Cache类
     *
     * @param cacheName
     * @return
     */
    public Cache getCache(String cacheName) {
        return manager.getCache(cacheName);
    }

    /**
     * 添加缓存数据
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public  void put(String cacheName, String key, Object value) {
        try {
            Cache cache = manager.getCache(cacheName);
            Element element = new Element(key, value);
            cache.put(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存数据
     *
     * @param cacheName
     * @param key
     * @return
     */
    public Object get(String cacheName, String key) {
        try {
            Cache cache = manager.getCache(cacheName);
            Element element = cache.get(key);
            return element == null ? null : element.getObjectValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除缓存数据
     *
     * @param cacheName
     * @param key
     */
    public void delete(String cacheName, String key) {
        try {
            Cache cache = manager.getCache(cacheName);
            cache.remove(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   static  {
        MaterialData materialData = new MaterialData();
       Map<String, String> map = null;
       try {
           File file = new File(filelo+"data/");
           map = new HashMap<>();
           for (File f : file.listFiles()) {
               String[] str = f.getName().split("\\.");
               if(str[str.length-1].equals("xml")){
                   String name = f.getName();
                   Document document = materialData.getDocument(filelo+"data/"+name);
                   org.dom4j.Element rootElement = document.getRootElement();
                   List<org.dom4j.Element> objects = rootElement.element("Objects").elements();
                   for (org.dom4j.Element object : objects) {
                       if (object.attributeValue("Action").equals("REGIST") || object.attributeValue("Action").equals("UPDATE")) {
                           if (object.attributeValue("ElementType").equals("Category")) {
                               map.put(object.attributeValue("Code"),object.attributeValue("ParentCode"));
                           }
                       }
                   }
               }
           }
       } catch (Exception e) {
       }
        Object obj = JSONArray.toJSON(map);
        String jsonObject = obj.toString();
        EhCacheUtil ehCacheUtil = EhCacheUtil.getInstance();
        ehCacheUtil.put(TAXI_CACHE, GROUP_VEHICLE_TREE_KEY,jsonObject);
    }

}