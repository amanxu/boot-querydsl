package com.jpa.querydsl.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * @description:
 * @author: niexiaoxu
 * @date: 2020/2/28 10:12 AM
 */
@Slf4j
@Component
@Configuration
public class JpaQueryFactoryConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean(name = "jpaQueryFactory")
    public JPAQueryFactory initFactory() {
        log.debug("queryFactory init success");
        return new JPAQueryFactory(entityManager);
    }
}
