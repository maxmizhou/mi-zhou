//package com.mi.zhou.service;
//
//import net.lz1998.pbbot.bot.Bot;
//import onebot.OnebotEvent;
//
///**
// * @author miZhou
// * @version 1.0
// * @date 2023/3/31
// */
//
//public interface RobotService {
//
//    /**
//     * 校验是否在对话中 或则开启对话
//     *
//     * @param userId 用户id
//     * @return service名称
//     */
//    String checkTalk(Long selfId, Long userId, Long groupId, boolean isCallMe);
//
//    void executorService(String serviceName,OnebotEvent.GroupMessageEvent event, Bot bot, String rawMessage);
//
//    String openTalk(Long selfId, Long userId, Long groupId, String rawMessage);
//}
