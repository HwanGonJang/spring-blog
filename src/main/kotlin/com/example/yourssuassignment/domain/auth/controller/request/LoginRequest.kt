package com.example.yourssuassignment.domain.auth.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.Size

class LoginRequest(
    @Schema(
        description = """[필수] 고객의 이메일입니다. <br />""",
    )
    @field:Email
    val email: String,

    @Schema(description = "[필수] 고객의 비밀번호입니다.")
    @field:Size(min = 6, max = 13, message = "잘못된 입력입니다.")
    val password: String,
)
