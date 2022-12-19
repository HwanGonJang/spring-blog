package com.example.yourssuassignment.domain.user.repository

import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByUser(
        user: User
    ): List<Article>

    fun findAllByUserIn(
        users: List<User>
    ): List<Article>
}
