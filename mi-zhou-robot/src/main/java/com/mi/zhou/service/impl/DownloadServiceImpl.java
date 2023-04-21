package com.mi.zhou.service.impl;


import com.alibaba.fastjson2.JSON;
import com.jayway.jsonpath.JsonPath;
import com.mi.zhou.model.ParseEntity;
import com.mi.zhou.service.DownloadService;
import com.mi.zhou.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/14
 */
@Slf4j
@Service
public class DownloadServiceImpl implements DownloadService {
    @Value("${spring.bot.parsePath}")
    private String parseFileBasePath;


    @Override
    public String getSourceHtml(String url, Map<String, Object> headers) {
        //获取html
        try {
            return HttpUtil.get(HttpUtil.getRedirectUrl(url), headers, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, List<String>> parseHtml(String sourceHtml, String parseFile) {
        Map<String, List<String>> resultMap = new HashMap<>();
        try {
            File file = new File(parseFileBasePath + parseFile);
            String jsonStr = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            ParseEntity parse = JSON.parseObject(jsonStr, ParseEntity.class);
            List<String> matchList = parse.getMatchList();
            for (String regex : matchList) {
                sourceHtml = regexValue(sourceHtml, regex);
            }
            if (parse.isUrlDecode()) {
                sourceHtml = URLDecoder.decode(sourceHtml, StandardCharsets.UTF_8);
            }
            List<ParseEntity.Regex> jsonPath = parse.getJsonPath();
            for (ParseEntity.Regex regex : jsonPath) {
                String regexValue = regex.getRegex();
                String regexType = regex.getType();
                Object read = JsonPath.parse(sourceHtml).read(regexValue);
                List<String> listUrl = JSON.parseArray(JSON.toJSONString(read), String.class);
                resultMap.put(regexType, listUrl);
            }
        } catch (Exception e) {
            log.error("html解析失败:{}", sourceHtml);
            return resultMap;
        }
        return resultMap;
    }

    @Override
    public void downloadVideo(String url, String fileName)  {
        File file = new File(fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        HttpUtil.download(url, fileName);


    }

    private String regexValue(String sourceHtml, String regex) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(sourceHtml);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return sourceHtml;
    }
}
