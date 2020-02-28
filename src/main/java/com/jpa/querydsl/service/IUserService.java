package com.jpa.querydsl.service;

import com.jpa.querydsl.model.UserBean;
import com.jpa.querydsl.model.dto.UserCreateDTO;

import java.util.List;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/28 10:37 AM
 */
public interface IUserService {


    /**
     * 新增用户
     *
     * @param userCreateDTO
     * @return
     */
    UserBean createUser(UserCreateDTO userCreateDTO);

    /**
     * 新增用户
     *
     * @param userCreateDTO
     * @return
     */
    Long updateUser(UserCreateDTO userCreateDTO);


    /**
     * 查询全部数据并根据id倒序
     *
     * @return
     */
    List<UserBean> queryAllByDsl();


    /**
     * 查询全部数据并根据id倒序
     *
     * @return
     */
    List<UserBean> queryAllByJpa();


    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    UserBean queryById(Long id);

    /**
     * 根据名称模糊查询
     *
     * @param userName
     * @return
     */
    List<UserBean> queryListByName(String userName);
}
