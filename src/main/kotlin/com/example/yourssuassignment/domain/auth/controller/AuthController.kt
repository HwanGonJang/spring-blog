package com.example.yourssuassignment.domain.auth.controller

import com.example.yourssuassignment.domain.auth.controller.request.LoginRequest
import com.example.yourssuassignment.domain.auth.controller.response.LoginResponse
import com.example.yourssuassignment.domain.auth.facade.AuthFacade
import com.example.yourssuassignment.domain.auth.controller.request.CreateUserRequest
import com.example.yourssuassignment.domain.user.facade.UserFacade
import com.example.yourssuassignment.dto.TokenDto
import com.example.yourssuassignment.dto.UserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userFacade: UserFacade,
    private val authFacade: AuthFacade,
) {
    @Operation(
        summary = "고객 회원가입",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "409",
                description = "해당하는 고객이 이미 존재합니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],

        )
    @PostMapping("/signUp")
    fun createUser(
        @RequestBody
        @Valid
        createUserRequest: CreateUserRequest,
    ): UserDto = userFacade.createUser(
        email = createUserRequest.email,
        password = createUserRequest.password,
        username = createUserRequest.username,
        role = createUserRequest.role,
    )

    @Operation(
        summary = "로그인",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "409",
                description = "해당하는 고객이 이미 존재합니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @PatchMapping("/signIn")
    fun login(
        @RequestBody
        loginRequest: LoginRequest,
    ): LoginResponse = authFacade.login(
        email = loginRequest.email,
        password = loginRequest.password,
    )


    @Operation(
        summary = "Access Token으로 재발급",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 토큰입니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @PatchMapping("/reissueWithAccessToken")
    fun reissueWithAccessToken(): LoginResponse = authFacade.reissueWithAccessToken()

    @Operation(
        summary = "Refresh Token으로 재발급",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 토큰입니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @PatchMapping("/reissueWithRefreshToken")
    fun reissueWithRefreshToken(
        @RequestBody
        tokenDto: TokenDto,
    ): LoginResponse = authFacade.reissueWithRefreshToken(
        accessToken = tokenDto.accessToken,
        refreshToken = tokenDto.refreshToken,
    )
}