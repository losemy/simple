package com.github.losemy.simple.run;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: simple
 * Created by lose on 2019/8/26 20:57
 * 不要扫描太多路径
 */
@SpringBootApplication
@ComponentScan("com.github.losemy.simple")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableTransactionManagement
@EnableDubbo(scanBasePackages = "com.github.losemy.simple.biz")
@EnableElasticsearchRepositories(basePackages = "com.github.losemy.simple")
//@EnableSSO
public class SimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class,args);
    }
}
