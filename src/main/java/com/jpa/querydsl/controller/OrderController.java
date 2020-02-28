package com.jpa.querydsl.controller;

import com.jpa.querydsl.model.dto.UserOrderDTO;
import com.jpa.querydsl.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Resource
    private IOrderService orderService;

    @ApiOperation(value = "查询订单信息，关联查询用户信息,并通过自定义字段接收")
    @GetMapping(value = "findOrderInfo")
    public UserOrderDTO queryOrderDetail(@RequestParam("id") Long id) {

        return orderService.queryOrderDetail(id);
    }
}
