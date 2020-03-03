package com.jpa.querydsl.repository;

import com.jpa.querydsl.model.UserOrderBean;

import java.util.List;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/29 1:35 PM
 */
public interface OrderJpaRepository extends BaseJpaRepository<UserOrderBean> {


    /**
     * 根据用户ID和商品ID查询
     * @param userId
     * @param productId
     * @return
     */
    List<UserOrderBean> findByUserIdAndProductId(Long userId, Long productId);

}
