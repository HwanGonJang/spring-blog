package com.example.yourssuassignment.domain.user.facade

import com.example.yourssuassignment.common.util.PasswordEncryptionUtil
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.UserDto
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserFacade(
    private val userService: UserService,
) {
    @Transactional
    fun createUser(
        email: String,
        password: String,
        username: String,
    ): UserDto = userService.createUser(
        email = email,
        password = password,
        username = username,
    ).let {
        UserDto(
            email = it.email,
            username = it.username,
        )
    }

    @Transactional
    fun deleteUser(
        email: String,
        password: String,
    ) {
        val user = userService.getByEmail(
            email = email,
        )

        PasswordEncryptionUtil.isEqualToEncryptedPassword(
            password = password,
            encryptedPassword = user.password,
        )

        userService.delete(user)
    }
}