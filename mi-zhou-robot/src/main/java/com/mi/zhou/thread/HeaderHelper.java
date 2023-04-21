package com.mi.zhou.thread;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/17
 */
@Slf4j
public class HeaderHelper {

    private static final Map<String, Object> headerMap = new ConcurrentHashMap<>();

    public static Map<String, Object> getHeaderMap() {
        return headerMap;
    }

    public static void start(String fileName) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String jsonString = FileUtils.readFileToString(new File(fileName), "UTF-8");
                    JSONObject entries = JSON.parseObject(jsonString);
                    entries.forEach((key, value) -> {
                        headerMap.put(key, value.toString());
                    });
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("header-helper");
        thread.start();
    }
}
