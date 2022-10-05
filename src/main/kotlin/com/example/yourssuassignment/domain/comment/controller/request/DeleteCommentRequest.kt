package com.example.yourssuassignment.domain.comment.controller.request

import io.swagger.v3.oas.annotations.media.Schema

data class DeleteCommentRequest(
    @Schema(
        description = """[필수] 고객의 이메일입니다.""",
    )
    val email: String,

    @Schema(description = """[필수] 고객의 비밀번호입니다.""")
    val password: String,
)