package com.example.yourssuassignment

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class QueryDslConfiguration(
    private val entityManager: EntityManager
) {
    @get:Bean
    val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)
}
