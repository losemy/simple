package com.github.losemy.simple.facade;

import com.github.losemy.simple.facade.annotation.ParamValid;
import com.github.losemy.simple.facade.model.User;
import com.github.losemy.simple.facade.resp.CommonResp;

/**
 * @author lose
 * @date 2019-09-06
 **/
public interface UserFacade {

    /**
     * 添加用户
     * @param user
     * @return
     */
    CommonResp addUser(@ParamValid(User.Add.class) User user);

}
