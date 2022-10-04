package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.repository.UserRepository
import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.UserAlreadyExistException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
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
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            email = email,
            password = password,
            username = username,
        )

        return userRepository.save(user)
    }
}