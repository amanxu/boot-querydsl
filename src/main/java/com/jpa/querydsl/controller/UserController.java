package com.jpa.querydsl.controller;

import com.jpa.querydsl.model.UserBean;
import com.jpa.querydsl.model.dto.UserCreateDTO;
import com.jpa.querydsl.respository.UserJpaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/27 3:41 PM
 */
@Slf4j
@Api(description = "QueryDsl学习测试类")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserJpaRepository userJpaRepository;

    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/create")
    public UserBean createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return userJpaRepository.createUser(userCreateDTO);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/update")
    public Long updateUser(@RequestBody UserCreateDTO userCreateDTO) {

        return userJpaRepository.updateUser(userCreateDTO);
    }

    @ApiOperation(value = "查询全部数据并根据id倒序")
    @PostMapping(value = "/queryAll")
    public List<UserBean> queryAll() {
        return userJpaRepository.queryAll();
    }

    @ApiOperation(value = "根据ID查询详情")
    @GetMapping(value = "findById")
    public UserBean queryById(@RequestParam("id") Long id) {
        return userJpaRepository.queryById(id);
    }

    @ApiOperation(value = "根据名称模糊查询")
    @GetMapping(value = "findByUserName")
    public List<UserBean> queryListByName(@RequestParam("userName") String userName) {
        return userJpaRepository.queryListByName(userName);
    }
}