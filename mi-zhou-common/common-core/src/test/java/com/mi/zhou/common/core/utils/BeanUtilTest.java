package com.mi.zhou.common.core.utils;

import com.mi.zhou.common.core.utils.BeanUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/19
 */
public class BeanUtilTest {
    private final static Logger log = LoggerFactory.getLogger(BeanUtilTest.class);


    @Test
    public void beanTest() {
        String s = BeanUtil.newInstance(String.class);
        log.info("{}",s.getClass());
    }
}
