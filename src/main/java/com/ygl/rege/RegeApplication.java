package com.ygl.rege;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement //开启事务扫描
@EnableCaching //开启spring 注解缓存功能
//@ServletComponentScan //扫描servlet组件（如filter过滤器）
public class RegeApplication extends SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegeApplication.class,args);
        log.info("项目启动成功.....");
    }
}
