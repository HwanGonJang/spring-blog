package com.example.yourssuassignment.domain.user.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Schema(name = "게시글 작성 API Request Body")
data class CreateArticleRequest(
    @Schema(
        description = """[필수] 고객의 이메일입니다.""",
    )
    @field:Email
    val email: String,

    @Schema(description = """[필수] 고객의 비밀번호입니다.""")
    @field:Size(min = 6, max = 13, message = "잘못된 입력입니다.")
    val password: String,

    @Schema(
        description = """[필수] 게시글의 제목입니다.""",
    )
    @field:NotBlank(message = "잘못된 입력입니다.")
    val title: String,

    @Schema(
        description = """[필수] 게시글의 내용입니다.""",
    )
    @field:NotBlank(message = "잘못된 입력입니다.")
    val content: String,
)