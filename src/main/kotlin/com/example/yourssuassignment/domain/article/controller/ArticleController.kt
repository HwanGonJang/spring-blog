package com.example.yourssuassignment.domain.user.controller

import com.example.yourssuassignment.domain.user.controller.request.CreateArticleRequest
import com.example.yourssuassignment.domain.user.facade.ArticleFacade
import com.example.yourssuassignment.dto.ArticleDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/articles")
class ArticleController(
    private val articleFacade: ArticleFacade,
) {
    @Operation(
        summary = "게시글 등록",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "OK",
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당하는 고객이 없습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
            ApiResponse(
                responseCode = "406",
                description = "비밀번호가 일치하지 않습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @PostMapping
    fun createArticle(
        @RequestBody
        @Valid
        createArticleRequest: CreateArticleRequest,
    ): ArticleDto {
        return articleFacade.createArticle(
            email = createArticleRequest.email,
            password = createArticleRequest.password,
            title = createArticleRequest.title,
            content = createArticleRequest.content,
        )
    }
}