package com.jpa.querydsl.service.impl;

import com.alibaba.fastjson.JSON;
import com.jpa.querydsl.model.QUserBean;
import com.jpa.querydsl.model.QUserOrderBean;
import com.jpa.querydsl.model.UserOrderBean;
import com.jpa.querydsl.model.dto.UserOrderDTO;
import com.jpa.querydsl.repository.OrderJpaRepository;
import com.jpa.querydsl.service.IOrderService;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/28 10:41 AM
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderJpaRepository orderJpaRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public UserOrderDTO queryOrderDetail(Long id) {

        QUserOrderBean qUserOrderBean = QUserOrderBean.userOrderBean;
        QUserBean qUserBean = QUserBean.userBean;

        // 方法一
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
        // 方法二（使用左连接,该写法存在问题，待解决）
        /*UserOrderDTO orderDTO = jpaQueryFactory
                .select(Projections.bean(UserOrderDTO.class,
                        qUserOrderBean.id,
                        qUserOrderBean.userId,
                        qUserOrderBean.productId,
                        qUserOrderBean.productName,
                        qUserOrderBean.createTime,
                        qUserBean.name,
                        qUserBean.address))
                .from(qUserOrderBean)
                .leftJoin(qUserBean).on(qUserBean.id.eq(qUserOrderBean.userId))
                .where(qUserOrderBean.id.eq(id))
                .fetchOne();
        log.info("左连接关联查询：\n{}", JSON.toJSONString(orderDTO, Boolean.TRUE));*/
        return userOrderDTO;
    }

    @Override
    public Page<UserOrderBean> queryPageOrderByUserId(Long userId, Integer offset, Integer pageSize) {

        Predicate predicate = QUserOrderBean.userOrderBean.userId.eq(userId)
                // geo 大于等于参数值，loe小于等于参数值
                .and(QUserOrderBean.userOrderBean.productId.goe(100))
                .and(QUserOrderBean.userOrderBean.productId.loe(101));

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = new PageRequest(offset, pageSize, sort);
        Page<UserOrderBean> orderBeanPage = orderJpaRepository.findAll(predicate, pageRequest);

        return orderBeanPage;
    }

    @Override
    public QueryResults<UserOrderBean> queryPageOrderByUserIdDsl(Long userId, Integer offset, Integer pageSize) {

        QueryResults<UserOrderBean> orderQueryResults = jpaQueryFactory.selectFrom(QUserOrderBean.userOrderBean)
                // 查询条件
                .where(QUserOrderBean.userOrderBean.userId.eq(userId))
                // 排序规则
                .orderBy(QUserOrderBean.userOrderBean.id.desc())
                .offset(offset)
                .limit(pageSize)
                .fetchResults();
        log.info("原生QueryDsl分页查询结果集：\n {}", JSON.toJSONString(orderQueryResults, Boolean.TRUE));
        return orderQueryResults;
    }


}
