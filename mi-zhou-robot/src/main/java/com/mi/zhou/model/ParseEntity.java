package com.mi.zhou.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/14
 */
@Slf4j
@Data
public class ParseEntity {


    private List<String> matchList;

    private boolean urlDecode;
    private List<Regex> jsonPath;

    @Data
    public static class Regex {
        private String type;
        private String regex;

    }
}
