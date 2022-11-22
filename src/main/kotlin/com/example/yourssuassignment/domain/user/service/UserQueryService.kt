package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.application.querydsl.QueryDslExtension.andIf
import com.example.yourssuassignment.common.enums.UserRole
import com.example.yourssuassignment.domain.user.entity.QUser
import com.example.yourssuassignment.domain.user.entity.User
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserQueryService(
    private val queryFactory: JPAQueryFactory,
) {
    fun getUsersByInquiry(
        email: String?,
        username: String?,
        createdAtStart: LocalDateTime?,
        createdAtEnd: LocalDateTime?,
        updatedAtStart: LocalDateTime?,
        updatedAtEnd: LocalDateTime?,
    ): List<User> {
        val user = QUser.user

        val conditions =
            BooleanBuilder()
                .and(user.userRole.eq(UserRole.USER))
                .andIf(email != null) { user.email.eq(email) }
                .andIf(username != null) { user.username.eq(username) }
                .andIf(createdAtStart != null) { user.createdDate.after(createdAtStart) }
                .andIf(createdAtEnd != null) { user.createdDate.before(createdAtEnd) }
                .andIf(updatedAtStart != null) { user.createdDate.after(updatedAtStart) }
                .andIf(updatedAtEnd != null) { user.createdDate.before(updatedAtEnd) }

        return queryFactory
            .selectFrom(user)
            .where(conditions)
            .orderBy(user.id.desc())
            .fetch()
    }


}