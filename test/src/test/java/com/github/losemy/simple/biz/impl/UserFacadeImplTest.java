package com.github.losemy.simple.biz.impl;

import com.github.losemy.simple.AbstractSpringBootTest;
import com.github.losemy.simple.common.mapper.BeanMapper;
import com.github.losemy.simple.dal.dao.UserDAO;
import com.github.losemy.simple.dal.model.UserDO;
import com.github.losemy.simple.facade.UserFacade;
import com.github.losemy.simple.facade.model.User;
import com.github.losemy.simple.facade.resp.CommonResp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

/**
 * @author lose
 * @date 2019-10-30
 **/
@ContextConfiguration(classes = {
        UserFacade.class
})
public class UserFacadeImplTest extends AbstractSpringBootTest {

    /**
     * MockBean 会取代实际使用的bean
     * SpyBean 只对修改做增强或者说改变，其他保持不变
     */

    @MockBean
    private UserDAO userDAO;

    @Autowired
    private UserFacade userFacade;


    @Test
    public void addUser(){
        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setName(UUID.randomUUID().toString());
        user.setEmail("adssd@123.com");
        user.setPassword("123");
//        spy(userFacade);
        //thenThrow 只有对应方法显示声明throw才有效 对外部行为进行模拟
        //dubbo 不需要也不应该抛出异常
        UserDO userDO = BeanMapper.map(user,UserDO.class);
        doThrow(new RuntimeException("我想看到你")).when(userDAO).insert(userDO);

        CommonResp resp = userFacade.addUser(user);

        assertEquals(resp.getRespCode(),"400999");

    }
}