package com.ygl.rege;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
//@ServletComponentScan //扫描servlet组件（如filter过滤器）
public class RegeApplication extends SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegeApplication.class,args);
        log.info("项目启动成功.....");
    }
}
