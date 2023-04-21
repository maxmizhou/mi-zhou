package com.mi.zhou.intercepter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lz1998.pbbot.handler.BotSessionInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/3/28
 */
@Primary
@Component
@Slf4j
public class RobotInterceptor extends BotSessionInterceptor {
    @SneakyThrows
    @Override
    public boolean checkSession(@NotNull WebSocketSession session) {
        HttpHeaders headers = session.getHandshakeHeaders();
        String botId = headers.getFirst("x-self-id");
        log.info("新的连接" + botId);
        if ("123".equals(botId)) {
            session.close();
            return false; // 禁止连接
        }
        return true; // 正常连接
    }
}
