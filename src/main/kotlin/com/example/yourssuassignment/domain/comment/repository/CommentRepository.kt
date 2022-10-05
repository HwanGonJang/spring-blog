package com.example.yourssuassignment.domain.comment.repository

import com.example.yourssuassignment.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByIdAndArticleId(
        commentId: Long,
        articleId: Long,
    ): Comment?
}