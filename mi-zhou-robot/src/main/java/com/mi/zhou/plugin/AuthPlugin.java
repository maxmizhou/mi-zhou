package com.mi.zhou.plugin;

import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/8
 */
@Slf4j
@Component
public class AuthPlugin extends BotPlugin {

    String callMeMsg = "<at qq=\"%s\"/>";
    /*
    管理员
     */
    private static final Set<Long> adminList = new HashSet<>() {{
        add(978625357L);
        add(2842155042L);
    }};

    /*
    授权组
     */
    private volatile Set<Long> includedGroupId = new HashSet<>() {{
        add(100781687L);
    }};
    /*
      授权人
         */
    private volatile Set<Long> includedUserId = new HashSet<>() {{
        add(978625357L);
    }};

    private volatile Set<String> adminOperateType = new HashSet<>() {{
        add("添加用户");
        add("移除用户");
        add("添加群");
        add("移除加群");
        add("添加管理员");
        add("移除管理员");
    }};

    @Override

    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage().trim();
        if (!StringUtils.hasLength(rawMessage)) {
            return MESSAGE_BLOCK;
        }

        //如果是admin可以执行一些操作
        if (operateByAdmin(bot, event)) {
            return MESSAGE_BLOCK;
        }
        //admin 和（授权群 +授权人放行）
        if (checkIsAdmin(event.getUserId()) || checkIsIncludeGroupAndUser(event.getGroupId(), event.getUserId())) {
            return MESSAGE_IGNORE;
        }
        //转发消息
        return MESSAGE_BLOCK;
    }

    private boolean operateByAdmin(Bot bot, OnebotEvent.GroupMessageEvent event) {
        if (!checkIsAdmin(event.getUserId())) {
            return false;
        }
        String callMeLabelExpression = String.format(callMeMsg, bot.getSelfId());
        String callMeStrExpression = "@" + bot.getLoginInfo().getNickname();
        String rowMsg = event.getRawMessage().replace(callMeLabelExpression, "").replace(callMeStrExpression, "").trim();
        if (!StringUtils.hasLength(rowMsg)) {
            return false;
        }

        for (String type : adminOperateType) {
            if (rowMsg.contains(type)) {
                adminOperate(bot, event, rowMsg, type);
                return true;
            }
        }
        return false;
    }

    private void adminOperate(Bot bot, OnebotEvent.GroupMessageEvent event, String rowMsg, String type) {
        boolean yesOrNo = true;
        String returnMsg = "%sQQ:%s %s";
        Long id = 0L;
        String strId = rowMsg.replace(type, "").trim();
        try {
            id = Long.parseLong(strId);
        } catch (NumberFormatException e) {
            yesOrNo = false;
        }
        returnMsg = String.format(returnMsg, type, strId, yesOrNo ? "成功" : "失败");
        if (yesOrNo) {
            switch (type) {
                case "添加用户":
                    addUserId(id);
                    break;
                case "移除用户":
                    delUserId(id);
                    break;
                case "添加群":
                    addGroupId(id);
                    break;
                case "移除加群":
                    delGroupId(id);
                    break;
                case "添加管理员":
                    addAdmin(id);
                    break;
                case "移除管理员":
                    delAdmin(id);
                    break;
            }
        }
        Msg.builder().at(event.getUserId()).text(returnMsg).sendToGroup(bot, event.getGroupId());
    }


    public boolean checkIsAdmin(Long userId) {
        return adminList.contains(userId);
    }

    public boolean checkIsIncludeGroupAndUser(Long groupId, Long userId) {
        return includedGroupId.contains(groupId) && includedUserId.contains(userId);
    }

    public void addAdmin(Long userId) {
        adminList.add(userId);
    }


    public void addGroupId(Long groupId) {
        includedGroupId.add(groupId);
    }

    public void addUserId(Long userId) {
        includedUserId.add(userId);
    }


    public void delAdmin(Long userId) {
        adminList.remove(userId);
    }


    public void delGroupId(Long groupId) {
        includedGroupId.remove(groupId);
    }

    public void delUserId(Long userId) {
        includedUserId.remove(userId);
    }
}
