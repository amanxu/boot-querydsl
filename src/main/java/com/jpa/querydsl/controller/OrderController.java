package com.jpa.querydsl.controller;

import com.jpa.querydsl.model.QUserBean;
import com.jpa.querydsl.model.QUserOrderBean;
import com.jpa.querydsl.model.dto.UserOrderDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/27 6:01 PM
 */
@Slf4j
@Api(description = "QueryDsl多表关联|自定义BEAN接收结果集")
@RestController
@RequestMapping(value = "/user")
public class OrderController {

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

    @ApiOperation(value = "查询订单信息，关联查询用户信息,并通过自定义字段接收")
    @GetMapping(value = "findOrderInfo")
    public UserOrderDTO queryOrderDetail(@RequestParam("id") Long id) {

        QUserOrderBean qUserOrderBean = QUserOrderBean.userOrderBean;
        QUserBean qUserBean = QUserBean.userBean;

        UserOrderDTO userOrderDTO = queryFactory
                // 用户自定义BEAN接收查询结果
                .select(Projections.bean(UserOrderDTO.class,
                        qUserOrderBean.id,
                        qUserOrderBean.userId,
                        qUserOrderBean.productId,
                        qUserOrderBean.productName,
                        qUserOrderBean.createTime,
                        qUserBean.name,
                        qUserBean.address))
                .from(qUserOrderBean, qUserBean)
                // 多表关联查询
                .where(qUserOrderBean.id.eq(id)
                        .and(qUserOrderBean.userId.eq(qUserBean.id)))
                .fetchOne();
        return userOrderDTO;
    }
}
