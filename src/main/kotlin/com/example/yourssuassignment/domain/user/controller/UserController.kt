package com.example.yourssuassignment.domain.user.controller

import com.example.yourssuassignment.domain.user.controller.request.CreateUserRequest
import com.example.yourssuassignment.domain.user.facade.UserFacade
import com.example.yourssuassignment.dto.UserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userFacade: UserFacade,
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
    @PostMapping
    fun createUser(
        @RequestBody
        @Valid
        createUserRequest: CreateUserRequest,
    ): UserDto {
        val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
        return userFacade.createUser(
            email = createUserRequest.email,
            password = encoder.encode(createUserRequest.password),
            username = createUserRequest.username,
        )
    }
}