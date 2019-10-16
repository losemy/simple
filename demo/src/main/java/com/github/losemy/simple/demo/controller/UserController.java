package com.github.losemy.simple.demo.controller;

import com.alibaba.fastjson.JSON;
import com.github.losemy.simple.facade.UserFacade;
import com.github.losemy.simple.facade.model.User;
import com.github.losemy.simple.facade.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Description: simple
 * Created by lose on 2019/8/29 21:35
 */
@RestController
@Slf4j
public class UserController {

    @Reference(check = false, timeout = 5000, retries = 0)
    private UserFacade userFacade;

    @GetMapping("/addUser")
    public CommonResp index() {

        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setName(UUID.randomUUID().toString());
        user.setEmail("adssd@123.com");
        user.setPassword("123");
        log.info("user {}", JSON.toJSONString(user));
        return userFacade.addUser(user);

    }

}
