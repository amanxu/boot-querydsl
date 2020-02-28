package com.jpa.querydsl.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/28 8:38 AM
 */

@ApiModel(value = "新增用户如参")
@Setter
@Getter
@ToString
public class UserCreateDTO implements Serializable {

    private static final long serialVersionUID = -5793738039978052023L;

    private Long id;

    private String name;

    private Integer age;

    private String address;

    private String pwd;
}
