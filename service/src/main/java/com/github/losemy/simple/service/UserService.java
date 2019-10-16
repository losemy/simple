package com.github.losemy.simple.service;


import com.github.losemy.simple.dal.model.UserDO;
import com.github.losemy.simple.dal.mybatisplus.base.service.IService;
import com.github.losemy.simple.service.dto.UserDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
public interface UserService extends IService<UserDO> {

    /**
     * 查看用户是否存在
     * @param user
     * @return
     */
    UserDO findByNameAndPassword(UserDTO user);

    void testException();
}
