

## 框架集成
常用功能初步实现

>>代码风格 少使用@Component注解
需要的话使用
@Configuration + @Bean


>>todo
CAT 相关第三方集成移到jar

>>todo
加入es+hbase做搜索
以及seata等做分布式事务

>>vm参数
主要是为了设置sentinel需要环境
-Dcsp.sentinel.dashboard.server=192.168.0.110:29980 
-Dproject.name=simple 
-Djava.net.preferIPv4Stack=true 
es设置避免初始化失败
-Des.set.netty.runtime.available.processors=false


>>jvm参数
-Dcsp.sentinel.dashboard.server=192.168.0.110:29980 -Dproject.name=simple -Djava.net.preferIPv4Stack=true -Des.set.netty.runtime.available.processors=false

>>skywaking
注意配置文件对应到jar包的位置
-javaagent:/path/to/skywalking-agent/skywalking-agent.jar