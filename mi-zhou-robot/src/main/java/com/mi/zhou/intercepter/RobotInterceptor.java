package com.mi.zhou.intercepter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.handler.BotSessionInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import java.util.Set;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/3/28
 */
@Primary
@Component
@Slf4j
public class RobotInterceptor extends BotSessionInterceptor {
    public static final Set<String> excludeSet = Set.of("123", "456");

    @SneakyThrows
    @Override
    public boolean checkSession(@NotNull WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        String botId = headers.getFirst("x-self-id");
        if (StringUtils.isBlank(botId) || excludeSet.contains(botId)) {
            log.error("禁止连接，botId is :{}", botId);
            session.close();
            return false; // 禁止连接
        }
        return true; // 正常连接
    }
}
