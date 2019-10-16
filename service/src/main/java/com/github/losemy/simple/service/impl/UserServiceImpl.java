package com.github.losemy.simple.service.impl;


import com.github.losemy.cat.utils.aop.CatAnnotation;
import com.github.losemy.simple.common.constant.CodeEnum;
import com.github.losemy.simple.common.mapper.BeanMapper;
import com.github.losemy.simple.dal.dao.UserDAO;
import com.github.losemy.simple.dal.model.UserDO;
import com.github.losemy.simple.dal.mybatisplus.base.service.impl.ServiceImpl;
import com.github.losemy.simple.service.UserService;
import com.github.losemy.simple.service.dto.UserDTO;
import com.github.losemy.simple.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserDAO, UserDO> implements UserService {

    @Override
    @CatAnnotation
    public UserDO findByNameAndPassword(UserDTO user) {
        return baseMapper.selectByNameAndPassword(BeanMapper.map(user,UserDO.class));
    }

    @Override
    public void testException() {
        throw new ServiceException(CodeEnum.SERVICE_ERROR);
    }

}
