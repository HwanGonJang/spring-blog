package com.example.yourssuassignment.domain.user.facade

import com.example.yourssuassignment.domain.user.service.ArticleService
import com.example.yourssuassignment.dto.ArticleDto
import com.example.yourssuassignment.dto.UserDto
import org.springframework.stereotype.Service

@Service
class ArticleFacade(
    private val articleService: ArticleService,
) {
    fun createArticle(
        email: String,
        password: String,
        title: String,
        content: String,
    ): ArticleDto {
        val article = articleService.createArticle(
            email = email,
            password = password,
            title = title,
            content = content,
        )

        return ArticleDto(
            articleId = article.id,
            email = article.user.email,
            title = article.title,
            content = article.title,
        )
    }
}