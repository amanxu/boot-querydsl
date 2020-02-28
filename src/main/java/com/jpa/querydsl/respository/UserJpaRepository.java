package com.jpa.querydsl.respository;

import com.jpa.querydsl.model.QUserBean;
import com.jpa.querydsl.model.UserBean;
import com.jpa.querydsl.model.dto.UserCreateDTO;
import com.jpa.querydsl.service.UserJpa;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/27 5:57 PM
 */
@Slf4j
@Repository
public class UserJpaRepository {

    @Autowired
    private UserJpa userJPA;

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
        log.debug("queryFactory init success");
    }

    /**
     * 新增用户
     *
     * @param userCreateDTO
     * @return
     */
    public UserBean createUser(UserCreateDTO userCreateDTO) {
        UserBean userBean = new UserBean();
        // 模拟对象转换
        BeanUtils.copyProperties(userCreateDTO, userBean);

        // 持久化用户数据
        UserBean saveResult = userJPA.save(userBean);
        return saveResult;
    }

    /**
     * 新增用户
     *
     * @param userCreateDTO
     * @return
     */
    @Transactional
    public Long updateUser(UserCreateDTO userCreateDTO) {

        Assert.notNull(userCreateDTO.getId(), "update id is null");
        QUserBean qUserBean = QUserBean.userBean;

        // 持久化用户数据
        long updateResult = queryFactory.update(qUserBean)
                .where(qUserBean.id.eq(userCreateDTO.getId()))
                .set(qUserBean.name, userCreateDTO.getName())
                .set(qUserBean.age, userCreateDTO.getAge())
                .set(qUserBean.address, userCreateDTO.getAddress())
                .set(qUserBean.pwd, userCreateDTO.getPwd())
                .execute();
        return updateResult;
    }


    /**
     * 查询全部数据并根据id倒序
     *
     * @return
     */
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

    /**
     * 根据ID查询详情
     *
     * @param id
     * @return
     */
    public UserBean queryById(Long id) {
        QUserBean qUserBean = QUserBean.userBean;
        UserBean userBean = queryFactory.selectFrom(qUserBean)
                .where(qUserBean.id.eq(id))
                .fetchOne();
        return userBean;
    }

    /**
     * 根据名称模糊查询
     *
     * @param userName
     * @return
     */
    public List<UserBean> queryListByName(String userName) {
        QUserBean qUserBean = QUserBean.userBean;
        return queryFactory.selectFrom(qUserBean)
                .where(qUserBean.name.like(userName))
                .fetch();
    }

}
