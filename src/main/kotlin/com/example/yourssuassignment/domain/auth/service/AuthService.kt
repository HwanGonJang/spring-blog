package com.example.yourssuassignment.domain.auth.service

import com.example.yourssuassignment.application.jwt.TokenProvider
import com.example.yourssuassignment.dto.TokenDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val tokenProvider: TokenProvider,
) {
    fun getToken(
        email: String,
        password: String,
    ): TokenDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(
            email,
            password,
        )

        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return tokenProvider.createToken(authentication)
    }
}