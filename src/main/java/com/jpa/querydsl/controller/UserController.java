package com.jpa.querydsl.controller;

import com.jpa.querydsl.model.QUserBean;
import com.jpa.querydsl.model.UserBean;
import com.jpa.querydsl.model.dto.UserCreateDTO;
import com.jpa.querydsl.service.UserJPA;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private EntityManager entityManager;

    /**
     * JPA查询工厂
     */

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
        log.debug("queryFactory init success");
    }

    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/create")
    public UserBean createUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserBean userBean = new UserBean();
        // 模拟对象转换
        BeanUtils.copyProperties(userCreateDTO, userBean);

        // 持久化用户数据
        UserBean saveResult = userJPA.save(userBean);
        return saveResult;
    }

    @Transactional
    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/update")
    public Long updateUser(@RequestBody UserCreateDTO userCreateDTO) {

        Assert.notNull(userCreateDTO.getId(), "update id is null");
        QUserBean qUserBean = QUserBean.userBean;

        // 持久化用户数据
        long updateResult = queryFactory.update(qUserBean)
                .where(qUserBean.id.eq(userCreateDTO.getId()))
                .set(qUserBean.name, userCreateDTO.getName())
                .set(qUserBean.age, userCreateDTO.getAge())
                .set(qUserBean.address, userCreateDTO.getAddress())
                .set(qUserBean.pwd,userCreateDTO.getPwd())
                .execute();
        return updateResult;
    }


    @ApiOperation(value = "查询全部数据并根据id倒序")
    @PostMapping(value = "/queryAll")
    public List<UserBean> queryAll() {
        //使用queryDsl查询
        QUserBean qUserBean = QUserBean.userBean;
        //查询并返回结果集
        List<UserBean> userBeanList = queryFactory
                //查询源
                .selectFrom(qUserBean)
                //根据id倒序
                .orderBy(qUserBean.id.desc())
                //执行查询并获取结果集
                .fetch();
        return userBeanList;
    }

    @ApiOperation(value = "根据ID查询详情")
    @GetMapping(value = "findById")
    public UserBean queryById(@RequestParam("id") Long id) {
        QUserBean qUserBean = QUserBean.userBean;
        UserBean userBean = queryFactory.selectFrom(qUserBean)
                .where(qUserBean.id.eq(id))
                .fetchOne();
        return userBean;
    }

    @ApiOperation(value = "根据名称模糊查询")
    @GetMapping(value = "findByUserName")
    public List<UserBean> queryListByName(@RequestParam("userName") String userName) {
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory.selectFrom(qUserBean)
                .where(qUserBean.name.like(userName))
                .fetch();
    }
}