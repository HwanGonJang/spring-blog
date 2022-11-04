package com.example.yourssuassignment.domain.user.facade

import com.example.yourssuassignment.common.enum.UserRole
import com.example.yourssuassignment.common.util.PasswordEncryptionUtil
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.UserDto
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserFacade(
    private val userService: UserService,
) {
    // 유저 생성하기
    @Transactional
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
    ).let {     // User -> UserDto
        UserDto(
            email = it.email,
            username = it.username,
            role = it.userRole,
        )
    }

    // 유저 삭제하기
    @Transactional
    fun deleteUser(
        email: String,
        password: String,
    ) {
        val user = userService.getByEmail(
            email = email,
        )

        // 패스워드 검증
        PasswordEncryptionUtil.isEqualToEncryptedPassword(
            password = password,
            encryptedPassword = user.password,
        )

        userService.delete(user)
    }
}