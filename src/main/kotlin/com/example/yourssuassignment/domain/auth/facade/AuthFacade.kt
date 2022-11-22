package com.example.yourssuassignment.domain.auth.facade

import com.example.yourssuassignment.application.errorhandling.exception.TokenNotValidateException
import com.example.yourssuassignment.application.jwt.TokenProvider
import com.example.yourssuassignment.domain.auth.controller.response.LoginResponse
import com.example.yourssuassignment.domain.auth.service.AuthService
import com.example.yourssuassignment.domain.user.service.UserService
import org.springframework.stereotype.Service

@Service
class AuthFacade(
    private val authService: AuthService,
    private val userService: UserService,
    private val tokenProvider: TokenProvider
) {
    fun login(
        email: String,
        password: String,
    ): LoginResponse {
        val tokenDto = authService.getToken(
            email = email,
            password = password,
        )

        val user = userService.getByEmail(email)
        user.refreshToken = tokenDto.refreshToken

        val modifiedUser = userService.save(user)

        return LoginResponse(
            email = modifiedUser.email,
            username = modifiedUser.username,
            role = modifiedUser.userRole,
            accessToken = tokenDto.accessToken,
            refreshToken = modifiedUser.refreshToken,
        )
    }

    fun reissue(
        accessToken: String,
        refreshToken: String,
    ): LoginResponse {
        if (tokenProvider.validateToken(accessToken) == null) {
            throw TokenNotValidateException()
        }

        val authentication = tokenProvider.getAuthentication(
            accessToken = accessToken,
        )

        val user = userService.getByEmail(authentication.name)

        if (user.refreshToken != refreshToken)
            throw TokenNotValidateException()

        val tokenDto = tokenProvider.createToken(authentication)

        user.refreshToken = tokenDto.refreshToken
        val modifiedUser = userService.save(user)

        return LoginResponse(
            email = modifiedUser.email,
            username = modifiedUser.username,
            role = modifiedUser.userRole,
            accessToken = tokenDto.accessToken,
            refreshToken = modifiedUser.refreshToken,
        )
    }
}
