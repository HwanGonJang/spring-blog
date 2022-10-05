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
    fun createComment(
        articleId: Long,
        email: String,
        password: String,
        content: String,
    ): CommentDto {
        val user = userService.getByEmail(
            email = email,
        )

        PasswordEncryptionUtil.isEqualToEncryptedPassword(
            password = password,
            encryptedPassword = user.password,
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

    @Transactional
    fun updateComment(
        articleId: Long,
        commentId: Long,
        email: String,
        password: String,
        content: String,
    ): CommentDto {
        val user = userService.getByEmail(
            email = email,
        )

        PasswordEncryptionUtil.isEqualToEncryptedPassword(
            password = password,
            encryptedPassword = user.password,
        )

        val comment = commentService.getByArticleIdAndCommentId(
            commentId = commentId,
            articleId = articleId,
        )

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

    @Transactional
    fun deleteComment(
        articleId: Long,
        commentId: Long,
        email: String,
        password: String,
    ) {
        val user = userService.getByEmail(
            email = email,
        )

        PasswordEncryptionUtil.isEqualToEncryptedPassword(
            password = password,
            encryptedPassword = user.password,
        )

        val comment = commentService.getByArticleIdAndCommentId(
            articleId = articleId,
            commentId = commentId,
        )

        if (comment.user.id != user.id) throw DeleteUnauthorizedException()

        commentService.delete(comment)
    }
}