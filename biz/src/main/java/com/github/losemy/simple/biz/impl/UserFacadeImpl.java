package com.github.losemy.simple.biz.impl;

import com.alibaba.fastjson.JSON;
import com.github.losemy.simple.common.mapper.BeanMapper;
import com.github.losemy.simple.dal.model.UserDO;
import com.github.losemy.simple.facade.UserFacade;
import com.github.losemy.simple.facade.annotation.ParamValid;
import com.github.losemy.simple.facade.model.User;
import com.github.losemy.simple.facade.resp.CommonResp;
import com.github.losemy.simple.integration.es.repository.UserRepository;
import com.github.losemy.simple.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.dubbo.config.annotation.Service;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lose
 * @date 2019-09-06
 **/
@Service
@Slf4j
public class UserFacadeImpl implements UserFacade {

    private UserService userService;


    private RocketMQTemplate rocketMQTemplate;


    private UserRepository userRepository;

    @Autowired
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * 1. 在接口注解 方便查看校验
     * 2. 实现默认不带注解 需要自己加上
     * 3. 另外一个说法，可以不在接口定义 避免污染 因为最后生效的是实现类上的注解
     * @param user
     * @return
     */
    @Override
    public CommonResp addUser(@ParamValid(User.Add.class) User user) {
        //数据转换如何处理 convert不适合
        CommonResp commonResp = new CommonResp();
        UserDO userDO = BeanMapper.map(user,UserDO.class);

        System.out.println(userService.save(userDO));

//        userRepository.save(BeanMapper.map(user, UserES.class));


        rocketMQTemplate.asyncSend("demo-user:add", user, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("MQ result {}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable ex) {
                log.error("MQ send error", ex);
            }
        });


        return commonResp;
    }

    public static void main(String[] args) {
        try {
            CuratorFramework zkClient = CuratorFrameworkFactory.builder().
                    connectString("192.168.0.110:2181").
                    retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
            zkClient.start();


            String path = "/dubbo/metadata/com.github.losemy.simple.facade.UserFacade/provider/simple/service.data";

            System.out.println(new String(zkClient.getData().forPath(path)));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
