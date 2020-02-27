package com.jpa.querydsl.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/27 7:09 PM
 */
@ApiModel(value = "订单信息")
@Setter
@Getter
@ToString
public class UserOrderDTO implements Serializable {

    private static final long serialVersionUID = 7135260649300775973L;

    @ApiModelProperty(value = "订单ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "商品ID")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户地址")
    private String address;

}
