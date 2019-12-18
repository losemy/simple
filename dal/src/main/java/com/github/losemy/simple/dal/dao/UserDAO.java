package com.github.losemy.simple.dal.dao;


import com.github.losemy.simple.dal.model.UserDO;
import com.github.losemy.simple.dal.mybatisplus.base.mapper.SimpleBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
@Mapper
public interface UserDAO extends SimpleBaseMapper<UserDO> {

    /**
     * 获取user是否存在
     * @param user
     * @return
     */
    UserDO selectByNameAndPassword(UserDO user);

}
