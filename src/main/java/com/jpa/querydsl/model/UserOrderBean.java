package com.jpa.querydsl.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/27 5:51 PM
 */
@Data
@Entity
@Table(name = "user_order")
public class UserOrderBean implements Serializable {

    private static final long serialVersionUID = -5269492005776429560L;
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
