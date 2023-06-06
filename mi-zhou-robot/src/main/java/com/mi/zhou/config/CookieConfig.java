package com.mi.zhou.config;

import com.mi.zhou.thread.HeaderHelper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author miZhou
 * @version 1.0
 * @date 2023/4/17
 */
@Configuration
@ComponentScan("net.lz1998.pbbot")
public class CookieConfig {
    @Value("${spring.bot.parsePath}")
    private String parseFileBasePath;

    @PostConstruct
    public void init() {
        HeaderHelper.start(parseFileBasePath + "douyin-cookie.json");
    }
}
