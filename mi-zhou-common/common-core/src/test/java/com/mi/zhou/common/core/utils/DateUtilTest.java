package com.mi.zhou.common.core.utils;

import com.mi.zhou.common.core.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/18
 */

public class DateUtilTest {
    private static final Logger log = LoggerFactory.getLogger(DateUtilTest.class);

    @Test
    public void dateTest() {
        String s = DateUtil.formatDate(new Date());
        log.info(s);
    }

}
