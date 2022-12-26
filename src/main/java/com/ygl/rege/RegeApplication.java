package com.ygl.rege;

import ch.qos.logback.core.joran.action.AppenderAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RegeApplication extends SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegeApplication.class,args);
        log.info("项目启动成功.....");
    }
}
