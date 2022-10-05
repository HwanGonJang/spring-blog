package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.application.errorhandling.exception.UserNotFoundException
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
    ): User {
        val isUser = userRepository.findByEmailAndUsername(
            email = email,
            username = username,
        )

        if (isUser != null) throw UserAlreadyExistException()

        val user = User(
            id = 0,
            email = email,
            password = PasswordEncryptionUtil.encrypt(password),
            username = username,
        )

        return userRepository.save(user)
    }

    @Transactional
    fun delete(
        user: User,
    ) {
        userRepository.delete(user)
    }
}