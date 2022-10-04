package com.example.yourssuassignment.domain.user.repository

import com.example.yourssuassignment.domain.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository: JpaRepository<Article, Int> {
}