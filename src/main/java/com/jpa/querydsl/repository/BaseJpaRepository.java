package com.jpa.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/27 3:31 PM
 */
@NoRepositoryBean
public interface BaseJpaRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>, QueryDslPredicateExecutor<T> {
}