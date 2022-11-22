package com.example.yourssuassignment.domain.user.controller

import com.example.yourssuassignment.domain.user.controller.request.DeleteUserRequest
import com.example.yourssuassignment.domain.user.controller.response.GetUserResponse
import com.example.yourssuassignment.domain.user.facade.UserFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/users")
class UserController(
    private val userFacade: UserFacade,
) {
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
        summary = "고객 조회",
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
        ],
    )
    @GetMapping
    fun getUsersByInquiry(
        @RequestParam
        email: String?,
        @RequestParam
        username: String?,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        createdAtStart: LocalDate?,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        createdAtEnd: LocalDate?,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        updatedAtStart: LocalDate?,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        updatedAtEnd: LocalDate?,
    ): GetUserResponse {
        val userInfoDtos = userFacade.getUsersByInquiry(
            email = email,
            username = username,
            createdAtStart = createdAtStart?.atStartOfDay(),
            createdAtEnd = createdAtEnd?.atStartOfDay(),
            updatedAtStart = updatedAtStart?.atStartOfDay(),
            updatedAtEnd = updatedAtEnd?.atStartOfDay(),
        )

        return GetUserResponse(
            userInfoDtos = userInfoDtos
        )
    }

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
                description = "비밀번호가 일치하지 않습니다.",
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
