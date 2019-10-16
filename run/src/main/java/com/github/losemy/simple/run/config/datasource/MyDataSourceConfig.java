package com.github.losemy.simple.run.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Description: simple
 * Created by lose on 2019/8/26 20:00
 */
@MapperScan("com.github.losemy.simple.dal.dao")
@PropertySource("classpath:datasource.properties")
@Configuration
public class MyDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "simple.druid")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }


    /**
     * mybatis-plus 需要自定义的sqlSessionFactory才行
     * 不要自己定义会覆盖自动装配
     * @param druidDataSource
     * @return
     * @throws Exception
     */
//    @Bean
//    public SqlSessionFactory mysqlSessionFactory(DataSource druidDataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(druidDataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*Mapper.xml"));
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new CatMybatisInterceptor(jdbcUrl)});
//        return sqlSessionFactoryBean.getObject();
//    }


    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        return servletRegistrationBean;
    }
    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
