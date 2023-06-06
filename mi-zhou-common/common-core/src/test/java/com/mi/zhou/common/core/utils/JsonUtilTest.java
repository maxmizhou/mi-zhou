package com.mi.zhou.common.core.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/18
 */
public class JsonUtilTest {
    private final static Logger log = LoggerFactory.getLogger(JsonUtilTest.class);

    @Test
    public void jsonTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("AA", 111);
        map.put("BB", "222");
        //String jsonStr = JsonUtil.toJsonStr(map, false);
        //log.info(jsonStr);
    }
}
