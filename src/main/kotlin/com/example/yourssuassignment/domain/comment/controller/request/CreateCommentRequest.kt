package com.example.yourssuassignment.domain.comment.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

data class CreateCommentRequest(
    @Schema(
        description = """[필수] 고객의 이메일입니다.""",
    )
    val email: String,

    @Schema(description = """[필수] 고객의 비밀번호입니다.""")
    val password: String,

    @Schema(
        description = """[필수] 댓글의 내용입니다.""",
    )
    @field:NotBlank(message = "잘못된 입력입니다.")
    val content: String,
)
