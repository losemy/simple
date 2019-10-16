package test;

import cn.hutool.core.lang.UUID;
import com.github.losemy.simple.facade.UserFacade;
import com.github.losemy.simple.facade.model.User;
import com.github.losemy.simple.run.SimpleApplication;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author lose
 * @date 2019-10-12
 **/
@State(Scope.Thread)
public class Test {

    private ConfigurableApplicationContext context;

    private UserFacade userFacade;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(Test.class.getName()+".*")
                .warmupIterations(2).measurementIterations(2).forks(2).build();
        new Runner(options).run();
    }

    /**
     * setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public void init(){
        context = SpringApplication.run(SimpleApplication.class);
        userFacade = context.getBean(UserFacade.class);
    }

    /**
     * benchmark执行多次，此注解代表触发我们所要进行基准测试的方法
     */
    @Benchmark
    public void test(){

        User user =new User();
        user.setName(UUID.fastUUID().toString());
        user.setPassword("123");
        user.setEmail("123@123.com");
        userFacade.addUser(user);
    }
}
