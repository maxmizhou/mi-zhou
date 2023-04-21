//package com.mi.zhou.plugin;
//
//import com.mi.zhou.service.ChatGptService;
//import lombok.extern.slf4j.Slf4j;
//import net.lz1998.pbbot.bot.Bot;
//import net.lz1998.pbbot.bot.BotPlugin;
//import net.lz1998.pbbot.utils.Msg;
//import onebot.OnebotEvent;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * @author miZhou
// * @version 1.0
// * @date 2023/4/7
// */
//@Slf4j
//@Component
//public class CheatGptPlugin extends BotPlugin {
//    @Autowired
//    private ChatGptService chatGptService;
//
//    private final static Map<String, String> container = new ConcurrentHashMap<>();
//    Long chatGptUserId = 2681973140L;
//    Long chatGptGroupId = 682170229L;
//    private static AtomicBoolean OPEN_AI_MODEL = new AtomicBoolean(false);
//    String callMeMsg = "<at qq=\"%s\"/>";
//
//    @Override
//    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
//        //是否@我 并开启Ai模式了
//        if (!checkIsCallMe(bot, event)) {
//            return MESSAGE_IGNORE;
//        }
//        //是否是ChatGpt 回复消息
//        if (checkIsChatGptResponseMe(bot, event)) {
//            return MESSAGE_IGNORE;
//        }
//        String content = "";
//
//        try {
//            String rowMsg = event.getRawMessage().replace(String.format(callMeMsg, bot.getSelfId()), "").trim();
//            content = chatGptService.sendMessage(rowMsg).getContent();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (StringUtils.hasLength(content)) {
//            Msg.builder().at(event.getUserId()).text(content).sendToGroup(bot, event.getGroupId());
//            return MESSAGE_BLOCK;
//        }
//        forwardMessage(bot, event);
//
//        //转发消息
//        return MESSAGE_BLOCK;
//    }
//
//
//    private boolean checkIsCallMe(Bot bot, OnebotEvent.GroupMessageEvent event) {
//
//        String rawMessage = event.getRawMessage();
//        String callMe = String.format(callMeMsg, bot.getSelfId());
//        if (!rawMessage.contains(callMe)) {
//            return false;
//        }
//        String msg = rawMessage.replace(callMe, "").trim();
//        if (!StringUtils.hasLength(msg)) {
//            return false;
//        }
//        if ("开启AI模式".equalsIgnoreCase(msg)) {
//            OPEN_AI_MODEL.compareAndSet(false, true);
//            Msg.builder().at(event.getUserId()).text("AI模式开启成功").sendToGroup(bot, event.getGroupId());
//            return false;
//        }
//        if ("关闭AI模式".equalsIgnoreCase(msg)) {
//            OPEN_AI_MODEL.compareAndSet(true, false);
//            Msg.builder().at(event.getUserId()).text("AI模式关闭成功").sendToGroup(bot, event.getGroupId());
//            return false;
//        }
//        return OPEN_AI_MODEL.get();
//    }
//
//    private boolean checkIsChatGptResponseMe(Bot bot, OnebotEvent.GroupMessageEvent event) {
//        long groupId = event.getGroupId();
//        long userId = event.getUserId();
//        if (!(chatGptUserId.equals(userId) && chatGptGroupId.equals(groupId))) {
//            return false;
//        }
//        //保存消息体
//        String rawMessage = event.getRawMessage().replace(String.format(callMeMsg, bot.getSelfId()), "").trim();
//        int startIndexOf = rawMessage.indexOf("\r\n");
//        if (startIndexOf == -1) {
//            return true;
//        }
//        String key = rawMessage.substring(0, startIndexOf).trim();
//        if (!container.containsKey(key)) {
//            return true;
//        }
//        String groupId_userId = container.remove(key);
//        if (!StringUtils.hasLength(groupId_userId.trim())) {
//            return true;
//        }
//        String[] split = groupId_userId.split("_");
//        Msg.builder().at(Long.parseLong(split[1])).text(rawMessage).sendToGroup(bot, Long.parseLong(split[0]));
//        return true;
//    }
//
//
//    private void forwardMessage(Bot bot, OnebotEvent.GroupMessageEvent event) {
//        String rowMsg = event.getRawMessage().replace(String.format(callMeMsg, bot.getSelfId()), "").trim();
//        if (!StringUtils.hasLength(rowMsg)) {
//            return;
//        }
//        //Msg.builder().at(chatGptUserId).text(rowMsg).sendToGroup(bot, chatGptGroupId);
//        bot.sendGroupMsg(event.getGroupId(), "已转发", false);
//        //保存消息体
//        this.saveMessage(rowMsg, event.getGroupId() + "_" + event.getUserId());
//    }
//
//    private void saveMessage(String key, String value) {
//        container.put(key, value);
//    }
//}
