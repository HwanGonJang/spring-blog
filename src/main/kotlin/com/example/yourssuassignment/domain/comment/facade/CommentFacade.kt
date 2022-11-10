package com.example.yourssuassignment.domain.comment.facade

import com.example.yourssuassignment.application.errorhandling.exception.DeleteUnauthorizedException
import com.example.yourssuassignment.application.errorhandling.exception.UpdateUnauthorizedException
import com.example.yourssuassignment.common.util.PasswordEncryptionUtil
import com.example.yourssuassignment.domain.comment.service.CommentService
import com.example.yourssuassignment.domain.user.service.ArticleService
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.CommentDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentFacade(
    private val userService: UserService,
    private val articleService: ArticleService,
    private val commentService: CommentService,
) {
    // 댓글 작성하기
    fun createComment(
        articleId: Long,
        email: String,
        content: String,
    ): CommentDto {
        val user = userService.getByEmail(
            email = email,
        )

        val article = articleService.getById(articleId)

        return commentService.createComment(
            user = user,
            article = article,
            content = content,
        ).let {
            CommentDto(
                commentId = it.id,
                email = it.user.email,
                content = it.content,
            )
        }
    }

    // 댓글 수정하기
    @Transactional
    fun updateComment(
        articleId: Long,
        commentId: Long,
        email: String,
        content: String,
    ): CommentDto {
        val user = userService.getByEmail(
            email = email,
        )

        val comment = commentService.getByArticleIdAndCommentId(
            commentId = commentId,
            articleId = articleId,
        )

        // 수정 권한 검증
        if (comment.user.id != user.id) throw UpdateUnauthorizedException()

        comment.content = content

        return commentService.save(comment)
            .let {
                CommentDto(
                    commentId = it.id,
                    email = it.user.email,
                    content = it.content,
                )
            }
    }

    // 댓글 삭제하기
    @Transactional
    fun deleteComment(
        articleId: Long,
        commentId: Long,
        email: String,
    ) {
        val user = userService.getByEmail(
            email = email,
        )

        val comment = commentService.getByArticleIdAndCommentId(
            articleId = articleId,
            commentId = commentId,
        )

        // 삭제 권한 검증
        if (comment.user.id != user.id) throw DeleteUnauthorizedException()

        commentService.delete(comment)
    }
}