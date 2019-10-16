package com.github.losemy.simple.run.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.github.losemy.cat.utils.interceptor.CatMybatisInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: simple
 * Created by lose on 2019/8/27 9:09
 *
 * @author lose
 */
@Configuration
public class MybatisPlusAutoConfiguration {


    @Value("${jdbcUrl}")
    private String jdbcUrl;


    /**
     * 乐观锁
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 自动填充
     *
     * @return
     */
    @Bean
    public CommonMetaObjectHandler commonMetaObjectHandler() {
        return new CommonMetaObjectHandler();
    }

    @Bean
    public CatMybatisInterceptor catMybatisInterceptor(){
        return new CatMybatisInterceptor(jdbcUrl);
    }
}
