package com.example.yourssuassignment.domain.comment.service

import com.example.yourssuassignment.application.errorhandling.exception.CommentNotFoundException
import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.comment.entity.Comment
import com.example.yourssuassignment.domain.comment.repository.CommentRepository
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.repository.ArticleRepository
import com.example.yourssuassignment.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository,
) {
    // 게시글, 댓글 ID로 조회 -> 둘 다 맞아야 조회 가능
    fun getByArticleIdAndCommentId(
        commentId: Long,
        articleId: Long,
    ) = commentRepository.findByIdAndArticleId(
        commentId = commentId,
        articleId = articleId,
    ) ?: throw CommentNotFoundException()

    // 게시글 ID로 조회
    fun getById(
        commentId: Long,
    ) = commentRepository.findById(commentId).orElseThrow { throw CommentNotFoundException() }

    fun createComment(
        user: User,
        article: Article,
        content: String,
    ): Comment = save(
        Comment(
            id = 0,
            content = content,
            article = article,
            user = user,
        )
    )

    fun save(
        comment: Comment,
    ): Comment = commentRepository.save(comment)

    fun delete(
        comment: Comment,
    ) {
        commentRepository.delete(comment)
    }
}