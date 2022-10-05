package com.example.yourssuassignment.domain.user.controller

import com.example.yourssuassignment.domain.user.controller.request.CreateUserRequest
import com.example.yourssuassignment.domain.user.controller.request.DeleteUserRequest
import com.example.yourssuassignment.domain.user.facade.UserFacade
import com.example.yourssuassignment.dto.UserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.*
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
    ): UserDto = userFacade.createUser(
        email = createUserRequest.email,
        password = createUserRequest.password,
        username = createUserRequest.username,
    )

    @Operation(
        summary = "고객 탈퇴",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당하는 정보가 없습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
            ApiResponse(
                responseCode = "406",
                description = "비밀번호가 일치하지 않습니다. or 삭제할 권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @DeleteMapping
    fun deleteUser(
        @RequestBody
        deleteUserRequest: DeleteUserRequest,
    ) {
        userFacade.deleteUser(
            email = deleteUserRequest.email,
            password = deleteUserRequest.password,
        )
    }
}