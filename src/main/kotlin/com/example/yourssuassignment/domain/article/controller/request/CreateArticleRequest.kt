package com.example.yourssuassignment.domain.article.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

@Schema(name = "게시글 작성 API Request Body")
data class CreateArticleRequest(
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