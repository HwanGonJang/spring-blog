package com.example.yourssuassignment.domain.auth.service

import com.example.yourssuassignment.application.errorhandling.exception.UserNotFoundException
import com.example.yourssuassignment.domain.user.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import com.example.yourssuassignment.domain.user.entity.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException()
        return createUserDetails(user = user)
    }

    private fun createUserDetails(user: User): org.springframework.security.core.userdetails.User {
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            listOf(SimpleGrantedAuthority(user.userRole.role))
        )
    }
}
