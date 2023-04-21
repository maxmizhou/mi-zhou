package com.mi.zhou;

import com.mi.zhou.thread.HeaderHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/3/28
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class RobotApplication {
    public static void main(String[] args) {
        SpringApplication.run(RobotApplication.class, args);

    }
}
