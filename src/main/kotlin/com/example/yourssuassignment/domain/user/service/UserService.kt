package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.application.errorhandling.exception.UserNotFoundException
import com.example.yourssuassignment.common.enums.UserRole
import com.example.yourssuassignment.common.util.PasswordEncryptionUtil
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.repository.UserRepository
import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.UserAlreadyExistException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    // email로 유저 가져오기 -> null 이면 Exception
    @Transactional
    fun getByEmail(
        email: String,
    ): User = userRepository.findByEmail(
        email = email,
    ) ?: throw UserNotFoundException()

    @Transactional
    fun createUser(
        email: String,
        password: String,
        username: String,
        role: UserRole
    ): User {
        val user = userRepository.findByEmail(
            email = email,
        )

        // 이미 존재하는 고객이면 Exception
        if (user != null) throw UserAlreadyExistException()

        val userToSave = User(
            id = 0,     // auto_increment
            email = email,
            password = PasswordEncryptionUtil.encrypt(password),
            username = username,
            refreshToken = null,
            userRole = role,
        )

        return userRepository.save(userToSave)
    }

    @Transactional
    fun save(
        user: User
    ) = userRepository.save(user)

    @Transactional
    fun delete(
        user: User,
    ) {
        userRepository.delete(user)
    }
}