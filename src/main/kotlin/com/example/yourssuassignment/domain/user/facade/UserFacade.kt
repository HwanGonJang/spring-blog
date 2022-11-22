package com.example.yourssuassignment.domain.user.facade

import com.example.yourssuassignment.common.enums.UserRole
import com.example.yourssuassignment.common.util.PasswordEncryptionUtil
import com.example.yourssuassignment.domain.user.service.UserQueryService
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.UserDto
import com.example.yourssuassignment.dto.UserInfoDto
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserFacade(
    private val userService: UserService,
    private val userQueryService: UserQueryService,
) {
    // 유저 생성하기
    fun createUser(
        email: String,
        password: String,
        username: String,
        role: UserRole,
    ): UserDto = userService.createUser(
        email = email,
        password = password,
        username = username,
        role = role,
    ).let { // User -> UserDto
        UserDto(
            email = it.email,
            username = it.username,
            role = it.userRole,
        )
    }

    // 유저 삭제하기
    fun deleteUser(
        email: String,
    ) {
        val user = userService.getByEmail(
            email = email,
        )

        userService.delete(user)
    }

    fun getUsersByInquiry(
        email: String?,
        username: String?,
        createdAtStart: LocalDateTime?,
        createdAtEnd: LocalDateTime?,
        updatedAtStart: LocalDateTime?,
        updatedAtEnd: LocalDateTime?,
    ): List<UserInfoDto> = userQueryService.getUsersByInquiry(
        email = email,
        username = username,
        createdAtStart = createdAtStart,
        createdAtEnd = createdAtEnd,
        updatedAtStart = updatedAtStart,
        updatedAtEnd = updatedAtEnd,
    ).map {
        UserInfoDto(
            id = it.id,
            email = it.email,
            username = it.username,
            role = it.userRole,
            createdAt = it.createdDate,
            updatedAt = it.modifiedDate,
        )
    }
}
