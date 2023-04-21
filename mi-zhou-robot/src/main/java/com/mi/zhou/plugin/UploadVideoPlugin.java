package com.mi.zhou.plugin;

import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotBase;
import onebot.OnebotEvent;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/13
 */
@Slf4j
@Service
public class UploadVideoPlugin extends BotPlugin {


    @Value("${spring.web.resources.static-locations}")
    private String resourceLocation;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        String rawMessage = event.getRawMessage().trim();
        if (StringUtils.isBlank(rawMessage)) {
            return MESSAGE_BLOCK;
        }
        if (rawMessage.contains("sp")) {
            //上传视频
            String randomUrl = getRandomUrl("video", false);
            if (StringUtils.isBlank(randomUrl)) {
                Msg.builder().at(event.getUserId()).text("没有视频").sendToGroup(bot, event.getGroupId());
                return MESSAGE_BLOCK;
            }
            List<OnebotBase.Message> build = Msg.builder()
                    .video(
                            randomUrl + ".mp4",
                            randomUrl + ".jpeg", false)
                    .build();
            bot.sendGroupMsg(event.getGroupId(), build, false);
            return MESSAGE_BLOCK;
        }
        if (rawMessage.contains("st")) {
            String randomUrl = getRandomUrl("image", true);
            if (StringUtils.isBlank(randomUrl)) {
                Msg.builder().at(event.getUserId()).text("没有图片").sendToGroup(bot, event.getGroupId());
                return MESSAGE_BLOCK;
            }
            Msg.builder().image(randomUrl).sendToGroup(bot, event.getGroupId());
        }
        log.info("收到群消息: {}", event.getRawMessage());
        return super.onGroupMessage(bot, event);
    }

    private String getRandomUrl(String path, boolean isSuffix) {
        String url = "http://localhost:8081/%s/%s";
        String[] listFileName = new File(resourceLocation.replace("file:", "") + path).list();
        if (listFileName == null || listFileName.length == 0) {
            return null;
        }
        String randomFileName = listFileName[RandomUtils.nextInt(0, listFileName.length)];
        if (!isSuffix) {
            randomFileName = randomFileName.substring(0, randomFileName.lastIndexOf("."));
        }
        return String.format(url, path, randomFileName);
    }

}
