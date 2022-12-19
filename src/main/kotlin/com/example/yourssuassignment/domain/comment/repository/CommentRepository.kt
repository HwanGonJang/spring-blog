package com.example.yourssuassignment.domain.comment.repository

import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    // 댓글 조회
    fun findByIdAndArticleId(
        commentId: Long,
        articleId: Long,
    ): Comment?

    fun findAllByArticle(
        article: Article,
    ): List<Comment>

    fun findAllByArticleIn(
        articles: List<Article>,
    ): List<Comment>
}
