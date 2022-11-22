package com.example.yourssuassignment.domain.comment.controller

import com.example.yourssuassignment.domain.comment.controller.request.CreateCommentRequest
import com.example.yourssuassignment.domain.comment.controller.request.DeleteCommentRequest
import com.example.yourssuassignment.domain.comment.facade.CommentFacade
import com.example.yourssuassignment.dto.CommentDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/comments")
class CommentController(
    private val commentFacade: CommentFacade,
) {
    @Operation(
        summary = "댓글 작성",
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
    @PostMapping("/{articleId}")
    fun createComment(
        @Parameter(description = "[필수] 작성할 댓글의 게시글ID입니다.")
        @PathVariable
        articleId: Long,
        @RequestBody
        @Valid
        createCommentRequest: CreateCommentRequest,
    ): CommentDto = commentFacade.createComment(
        articleId = articleId,
        email = createCommentRequest.email,
        password = createCommentRequest.password,
        content = createCommentRequest.content,
    )

    @Operation(
        summary = "댓글 수정",
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
    @PatchMapping("/{articleId}/{commentId}")
    fun updateComment(
        @Parameter(description = "[필수] 수정할 게시글의 ID입니다.")
        @PathVariable
        articleId: Long,
        @Parameter(description = "[필수] 수정할 댓글의 ID입니다.")
        @PathVariable
        commentId: Long,
        @RequestBody
        @Valid
        createCommentRequest: CreateCommentRequest,
    ): CommentDto = commentFacade.updateComment(
        articleId = articleId,
        commentId = commentId,
        email = createCommentRequest.email,
        password = createCommentRequest.password,
        content = createCommentRequest.content,
    )

    @Operation(
        summary = "댓글 삭제",
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
    @DeleteMapping("/{articleId}/{commentId}")
    fun deleteComment(
        @Parameter(description = "[필수] 삭제할 게시글의 ID입니다.")
        @PathVariable
        articleId: Long,
        @Parameter(description = "[필수] 삭제할 댓글의 ID입니다.")
        @PathVariable
        commentId: Long,
        @RequestBody
        deleteCommentRequest: DeleteCommentRequest,
    ) {
        commentFacade.deleteComment(
            articleId = articleId,
            commentId = commentId,
            email = deleteCommentRequest.email,
            password = deleteCommentRequest.password,
        )
    }
}
