package com.github.losemy.simple.web.controller;


import com.github.losemy.simple.common.mapper.BeanMapper;
import com.github.losemy.simple.dal.model.UserDO;
import com.github.losemy.simple.service.UserService;
import com.github.losemy.simple.service.dto.UserDTO;
import com.github.losemy.simple.web.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDO> getAllUsers(){
        log.info("getAllUsers");
        PageHelper.startPage(1,10);
        return userService.list();
    }

    @GetMapping("/com.github.losemy.simple.test")
    public ResultVO test(){
        userService.testException();
        return ResultVO.ofSuccess(null);
    }

    @PostMapping("/add")
    public boolean addUser(@RequestBody @Validated(UserDTO.Add.class) UserDTO user){
        return userService.save(BeanMapper.map(user,UserDO.class));
    }

    @PostMapping("update")
    public boolean updateUser(@RequestBody @Validated(UserDTO.Add.class) UserDTO user){
        return userService.updateById(BeanMapper.map(user,UserDO.class));
    }
}

