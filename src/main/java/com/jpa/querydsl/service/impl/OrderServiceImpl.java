package com.jpa.querydsl.service.impl;

import com.jpa.querydsl.model.QUserBean;
import com.jpa.querydsl.model.QUserOrderBean;
import com.jpa.querydsl.model.dto.UserOrderDTO;
import com.jpa.querydsl.service.IOrderService;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/28 10:41 AM
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public UserOrderDTO queryOrderDetail(Long id) {

        QUserOrderBean qUserOrderBean = QUserOrderBean.userOrderBean;
        QUserBean qUserBean = QUserBean.userBean;

        UserOrderDTO userOrderDTO = jpaQueryFactory
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
