package com.example.yourssuassignment.domain.user.facade

import com.example.yourssuassignment.domain.user.service.ArticleService
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.UserDto
import org.springframework.stereotype.Service

@Service
class UserFacade(
    private val userService: UserService,
) {
    fun createUser(
        email: String,
        password: String,
        username: String,
    ): UserDto {
        val user = userService.createUser(
            email = email,
            password = password,
            username = username,
        )

        return UserDto(
            email = user.email,
            username = user.username,
        )
    }
}