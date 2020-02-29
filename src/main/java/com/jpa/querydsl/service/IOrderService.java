package com.jpa.querydsl.service;

import com.jpa.querydsl.model.UserOrderBean;
import com.jpa.querydsl.model.dto.UserOrderDTO;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/28 10:38 AM
 */
public interface IOrderService {

    /**
     * 查询订单信息，关联查询用户信息,并通过自定义字段接收
     *
     * @param id
     * @return
     */
    UserOrderDTO queryOrderDetail(Long id);

    /**
     * 根据用户ID分页查询用户
     *
     * @param userId
     * @param offset
     * @param pageSize
     * @return
     */
    Page<UserOrderBean> queryPageOrderByUserId(Long userId, Integer offset, Integer pageSize);

    /**
     * 使用原生Query DSL分页查询
     * @param userId
     * @param offset
     * @param pageSize
     * @return
     */
    QueryResults<UserOrderBean> queryPageOrderByUserIdDsl(Long userId, Integer offset, Integer pageSize);
}
