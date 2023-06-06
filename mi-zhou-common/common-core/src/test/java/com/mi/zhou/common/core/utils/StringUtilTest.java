package com.mi.zhou.common.core.utils;

import com.mi.zhou.common.core.constant.StringPool;
import com.mi.zhou.common.core.utils.StringUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/16
 */
public class StringUtilTest {
    private final static Logger log = LoggerFactory.getLogger(StringUtilTest.class);

    private final static Collection<CharSequence> charSequence = new ArrayList<>() {{
        add(" ");
        add("");
        add("xxxx");
        add("yyyy");
    }};

    @Test
    public void firstCharToLower() {
        String str = "GetSomething";
        String s = StringUtil.firstCharToLower(str);
        System.out.println("s = " + s);
    }

    @Test
    public void strIsNotBlank() {
        for (CharSequence sequence : charSequence) {
            boolean blank = StringUtil.isBlank(sequence);
            boolean notBlank = StringUtil.isNotBlank(sequence);
           // boolean isEmpty = StringUtil.isEmptyStr(sequence);
           // boolean isNotEmpty = StringUtil.isNotEmpty(sequence);
            //log.info("sequence:{},blank:{},notBlank:{},isEmpty:{},isNotEmpty:{}", sequence, blank, notBlank, isEmpty, isNotEmpty);
        }
        boolean anyBlank = StringUtil.isAnyBlank(charSequence);
        boolean noneBlank = StringUtil.isNoneBlank(charSequence);
        log.info("anyBlank:{},noneBlank:{}", anyBlank, noneBlank);
    }

    @Test
    public void isNumeric() {
        String numeric = "9999999999999999999999999999999";
        boolean numeric1 = StringUtil.isNumeric(numeric);
        log.info("{}", numeric1);
    }

    @Test
    public void format() {
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", "bbb");
        map.put("ccc", "ddd");
        String format = StringUtil.format("你是{}{}ZL", map);
        log.info(format);

    }

    @Test
    public void simpleMatch() {
        boolean xxxx = StringUtil.simpleMatch("x*", "xs");
        log.info("{}", xxxx);
    }

    @Test
    public void cleanText() {
        String sql = "SELECT *  from `user` where user_name=\"admin\" or 1=1";
        sql = StringUtil.cleanText(sql);
        log.info("{}", sql);
        sql="public=void&void";
        sql = StringUtil.cleanIdentifier(sql);
        log.info("{}", sql);
    }
    @Test
    public void random() {
        String random = StringUtil.random(10, RandomType.STRING);
        log.info("{}", random);
    }



}
