package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.repository.UserRepository
import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.UserAlreadyExistException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional
import kotlin.jvm.Throws
import kotlin.random.Random

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
        val random = Random(2022)

        val isUser = userRepository.findByEmailAndUsername(
            email = email,
            username = username,
        )

        if (isUser != null) throw UserAlreadyExistException()

        val user = User(
            id = 1,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            email = email,
            password = password,
            username = username,
        )

        return userRepository.save(user)
    }
}