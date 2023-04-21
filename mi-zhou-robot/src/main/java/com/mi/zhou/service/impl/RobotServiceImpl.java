//package com.mi.zhou.service.impl;
//
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.http.HttpUtil;
//import cn.hutool.http.Method;
//import cn.hutool.json.JSON;
//import cn.hutool.json.JSONArray;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.POJONode;
//import com.fasterxml.jackson.databind.node.TextNode;
//import com.jayway.jsonpath.JsonPath;;
//import com.mi.zhou.model.ServiceInfo;
//import com.mi.zhou.service.RobotService;
//import com.mi.zhou.util.TimeExpiredPoolCache;
//import net.lz1998.pbbot.bot.Bot;
//import onebot.OnebotEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.*;
//
///**
// * @author miZhou
// * @version 1.0
// * @date 2023/3/31
// */
//@Service
//public class RobotServiceImpl implements RobotService {
//    private final static Logger log = LoggerFactory.getLogger(RobotServiceImpl.class);
//    private static final String TALK_KEY = "mirai:talk_key:";
//
//    @Override
//    public String checkTalk(Long selfId, Long userId, Long groupId, boolean isCallMe) {
//        if (isCallMe) {
//            return null;
//        }
//        String serviceName = cacheGet(selfId, userId, groupId);
//        if (StringUtils.hasLength(serviceName)) {
//            this.cachePut(selfId, userId, groupId, serviceName);
//        }
//        return serviceName;
//    }
//
//    @Override
//    public String openTalk(Long selfId, Long userId, Long groupId, String rawMessage) {
//        Set<String> keySet = new HashSet<>();
//        if ("help".equals(rawMessage)) {
//            return "请输入服务名称:\n" + String.join("\n", keySet);
//        }
//        if (!keySet.contains(rawMessage)) {
//            return "没有找到对应的服务 请输入服务名称:\n" + String.join("\n", keySet);
//        }
//        this.cachePut(selfId, userId, groupId, rawMessage);
//        return rawMessage + ":对话已开启";
//    }
//
//    @Override
//    public void executorService(String serviceName, OnebotEvent.GroupMessageEvent event, Bot bot, String rawMessage) {
//        ServiceInfo serviceInfo = new ServiceInfo();
//        JsonNode node = requestService(serviceInfo);
//        bot.sendGroupMsg(event.getGroupId(), node.asText(), false);
//
//
//    }
//
//    private JsonNode requestService(ServiceInfo serviceInfo) {
//        //method
//        String method = serviceInfo.getMethod();
//        //param
//        Map<String, String> params = serviceInfo.getBody();
//        //headers
//        Map<String, String> headers = serviceInfo.getHeaders();
//        //url
//        String url = serviceInfo.getUrl();
//        //responseAssert
//        ServiceInfo.ResponseAssert responseAssert = serviceInfo.getResponseAssert();
//        HttpRequest request = HttpUtil.createRequest(Method.valueOf(method.toUpperCase()), url);
//        if (!CollectionUtils.isEmpty(headers)) {
//            request.addHeaders(headers);
//        }
//        if (!CollectionUtils.isEmpty(params)) {
//            if ("get".equals(method)) {
//                request.formStr(params);
//            }
//            if ("post".equals(method)) {
//                request.body(JSONUtil.toJsonStr(params));
//            }
//        }
//        HttpResponse httpResponse = request.execute();
//        if (httpResponse == null) {
//            throw new RuntimeException("请求失败");
//        }
//        String responseStr = httpResponse.body();
//        if (responseAssert == null || responseAssert.getStatus() == null || responseAssert.getJsonPathRegx() == null) {
//            return new TextNode(responseStr);
//        }
//        Integer status = responseAssert.getStatus();
//        if (status != httpResponse.getStatus()) {
//            throw new RuntimeException("响应失败");
//        }
//        return handleResponseAssert(responseAssert, responseStr);
//
//
//    }
//
//    private JsonNode handleResponseAssert(ServiceInfo.ResponseAssert responseAssert, String responseStr) {
//        String regx = responseAssert.getJsonPathRegx();
//        Object objJson = JsonPath.read(responseStr, regx);
//        String[] fileds = responseAssert.getFileds();
//        if (fileds == null || fileds.length == 0) {
//            return new POJONode(objJson);
//        }
//        JSONArray arrayJson = JSONUtil.parseArray(objJson);
//        List<JSONObject> listResultJson = new ArrayList<>();
//        for (Object obj : arrayJson) {
//            JSON parse = JSONUtil.parse(obj);
//            JSONObject resultJson = JSONUtil.createObj();
//            for (String filed : fileds) {
//                resultJson.set(filed, JsonPath.read(parse, "$." + filed));
//            }
//            listResultJson.add(resultJson);
//        }
//        return new POJONode(listResultJson);
//    }
//
//
//    private void cachePut(Long selfId, Long userId, Long groupId, String serviceName) {
//        TimeExpiredPoolCache.getInstance().put(TALK_KEY + selfId + ":" + userId + ":" + groupId, serviceName, 60 * 1000);
//    }
//
//
//    private String cacheGet(Long selfId, Long userId, Long groupId) {
//        return TimeExpiredPoolCache.getInstance().get(TALK_KEY + selfId + ":" + userId + ":" + groupId);
//    }
//
//
//}
