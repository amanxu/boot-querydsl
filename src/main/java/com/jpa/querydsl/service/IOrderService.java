package com.jpa.querydsl.service;

import com.jpa.querydsl.model.dto.UserOrderDTO;

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
}
