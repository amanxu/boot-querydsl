package com.jpa.querydsl.service.impl;

import com.jpa.querydsl.model.QUserBean;
import com.jpa.querydsl.model.UserBean;
import com.jpa.querydsl.model.dto.UserCreateDTO;
import com.jpa.querydsl.service.IUserService;
import com.jpa.querydsl.repository.UserJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/28 10:38 AM
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserJpaRepository userJpa;

    @Resource
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public UserBean createUser(UserCreateDTO userCreateDTO) {
        UserBean userBean = new UserBean();
        // 模拟对象转换
        BeanUtils.copyProperties(userCreateDTO, userBean);

        // 持久化用户数据
        UserBean saveResult = userJpa.save(userBean);
        return saveResult;
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public Long updateUser(UserCreateDTO userCreateDTO) {

        Assert.notNull(userCreateDTO.getId(), "update id is null");
        QUserBean qUserBean = QUserBean.userBean;

        // 持久化用户数据
        long updateResult = jpaQueryFactory.update(qUserBean)
                .where(qUserBean.id.eq(userCreateDTO.getId()))
                .set(qUserBean.name, userCreateDTO.getName())
                .set(qUserBean.age, userCreateDTO.getAge())
                .set(qUserBean.address, userCreateDTO.getAddress())
                .set(qUserBean.pwd, userCreateDTO.getPwd())
                .execute();
        return updateResult;
    }


    @Override
    public List<UserBean> queryAllByDsl() {
        //使用queryDsl查询
        QUserBean qUserBean = QUserBean.userBean;
        //查询并返回结果集
        List<UserBean> userBeanList = jpaQueryFactory
                //查询源
                .selectFrom(qUserBean)
                //根据id倒序
                .orderBy(qUserBean.id.desc())
                //执行查询并获取结果集
                .fetch();
        return userBeanList;
    }


    @Override
    public List<UserBean> queryAllByJpa() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return userJpa.findAll(sort);
    }


    @Override
    public UserBean queryById(Long id) {
        QUserBean qUserBean = QUserBean.userBean;
        UserBean userBean = jpaQueryFactory.selectFrom(qUserBean)
                .where(qUserBean.id.eq(id))
                .fetchOne();
        return userBean;
    }


    @Override
    public List<UserBean> queryListByName(String userName) {
        QUserBean qUserBean = QUserBean.userBean;
        return jpaQueryFactory.selectFrom(qUserBean)
                .where(qUserBean.name.like(userName))
                .fetch();
    }
}
