package com.example.yourssuassignment.domain.user.controller

import com.example.yourssuassignment.domain.article.facade.ArticleFacade
import com.example.yourssuassignment.domain.comment.controller.request.DeleteCommentRequest
import com.example.yourssuassignment.domain.user.controller.request.CreateArticleRequest
import com.example.yourssuassignment.dto.ArticleDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.*
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
    @PostMapping
    fun createArticle(
        @RequestBody
        @Valid
        createArticleRequest: CreateArticleRequest,
    ): ArticleDto = articleFacade.createArticle(
        email = createArticleRequest.email,
        password = createArticleRequest.password,
        title = createArticleRequest.title,
        content = createArticleRequest.content,
    )

    @Operation(
        summary = "게시글 수정",
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
                description = "비밀번호가 일치하지 않습니다. or 수정할 권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(hidden = true))),
            ),
        ],
    )
    @PatchMapping("/{articleId}")
    fun updateArticle(
        @Parameter(description = "[필수] 수정할 게시글의 ID입니다.")
        @PathVariable
        articleId: Long,
        @RequestBody
        @Valid
        createArticleRequest: CreateArticleRequest,
    ): ArticleDto = articleFacade.updateArticle(
        articleId = articleId,
        email = createArticleRequest.email,
        password = createArticleRequest.password,
        title = createArticleRequest.title,
        content = createArticleRequest.content,
    )

    @Operation(
        summary = "게시글 삭제",
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
    @DeleteMapping("/{articleId}")
    fun deleteArticle(
        @Parameter(description = "[필수] 삭제할 게시글의 ID입니다.")
        @PathVariable
        articleId: Long,
        @RequestBody
        @Valid
        deleteCommentRequest: DeleteCommentRequest,
    ) {
        articleFacade.deleteArticle(
            articleId = articleId,
            email = deleteCommentRequest.email,
            password = deleteCommentRequest.password,
        )
    }
}
