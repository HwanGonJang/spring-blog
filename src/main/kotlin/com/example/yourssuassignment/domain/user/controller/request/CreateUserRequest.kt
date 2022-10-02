package com.example.yourssuassignment.domain.user.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(name = "고객 등록 API Request Body")
data class CreateUserRequest(
    @Schema(
        description = """[필수] 고객의 이메일입니다. <br />""",
    )
    val email: String,

    @Schema(description = "[필수] 고객의 비밀번호입니다.")
    val password: String,

    @Schema(
        description = """[필수] 고객의 이름입니다.""",
    )
    val username: String,
)