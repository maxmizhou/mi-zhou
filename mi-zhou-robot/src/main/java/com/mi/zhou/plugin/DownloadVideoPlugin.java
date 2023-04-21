package com.mi.zhou.plugin;

import com.mi.zhou.service.DownloadService;
import com.mi.zhou.thread.HeaderHelper;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import net.lz1998.pbbot.utils.Msg;
import onebot.OnebotEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/14
 */
@Service
public class DownloadVideoPlugin extends BotPlugin {
    @Value("${spring.web.resources.static-locations}")
    private String resourceLocation;
    @Autowired
    private DownloadService downloadService;

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {

        String rawMessage = event.getRawMessage().trim();
        String str = "下载";
        if (!rawMessage.startsWith(str)) {
            return MESSAGE_IGNORE;
        }
        String url = rawMessage.replace("下载", "").trim();
        if (Pattern.compile("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+").matcher(url).find()) {
            Msg.builder().at(event.getUserId()).text("地址不正确:" + url).sendToGroup(bot, event.getGroupId());
            return MESSAGE_BLOCK;
        }
        //加密后的文件名
        String fileNameMd5 = DigestUtils.md5DigestAsHex(url.getBytes());
        File file = new File(resourceLocation.replace("file:", "") + "url/" + fileNameMd5 + ".txt");
        if (file.exists()) {
            Msg.builder().at(event.getUserId()).text("视频已下载:" + url).sendToGroup(bot, event.getGroupId());
            return MESSAGE_BLOCK;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String sourceHtml = downloadService.getSourceHtml(url, HeaderHelper.getHeaderMap());
        if (StringUtils.isBlank(sourceHtml)) {
            Msg.builder().at(event.getUserId()).text("视频下载失败:" + url).sendToGroup(bot, event.getGroupId());
            return MESSAGE_BLOCK;
        }
        Map<String, List<String>> urlMap = downloadService.parseHtml(sourceHtml, "douyin.json");
        if (urlMap == null || urlMap.isEmpty()) {
            Msg.builder().at(event.getUserId()).text("html解析失败:" + url).sendToGroup(bot, event.getGroupId());
            return MESSAGE_BLOCK;
        }
        int size = urlMap.values().stream().findFirst().get().size();
        List<String> linkedList = new LinkedList<>();
        long time = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            linkedList.add(time + "_" + i + "_" + RandomUtils.nextInt());
        }
        StringBuilder buffer = new StringBuilder();
        for (String type : urlMap.keySet()) {
            buffer.append("\r\n");
            List<String> listUrl = urlMap.get(type);
            buffer.append(type);
            for (int i = 0; i < listUrl.size(); i++) {
                buffer.append("\r\n");
                String urlVideo = listUrl.get(i);
                if (!urlVideo.startsWith("https:")) {
                    urlVideo = "https:" + urlVideo;
                }
                String fileName = resourceLocation.replace("file:", "") + "video/" + linkedList.get(i) + "." + type;
                buffer.append(fileName);
                downloadService.downloadVideo(urlVideo, fileName);
            }
        }
        try {
            FileUtils.write(file, buffer.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return MESSAGE_BLOCK;
        }
        Msg.builder().at(event.getUserId()).text("视频下载成功:" + url).sendToGroup(bot, event.getGroupId());
        return MESSAGE_BLOCK;
    }
}
