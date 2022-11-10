package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.application.errorhandling.exception.ArticleNotFoundException
import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.repository.ArticleRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
) {
    // id로 Article 조회 -> null 이면 Exception
    fun getById(
        articleId: Long,
    ): Article = articleRepository.findById(articleId).orElseThrow { throw ArticleNotFoundException() }

    @Transactional
    fun createArticle(
        email: String,
        title: String,
        content: String,
        user: User,
    ): Article = save(
        Article(
            id = 0,     // auto_increment
            content = content,
            title = title,
            user = user,
        )
    )

    @Transactional
    fun save(
        article: Article,
    ): Article = articleRepository.save(article)

    @Transactional
    fun delete(
        article: Article,
    ) {
        articleRepository.delete(article)
    }
}