package com.example.yourssuassignment.domain.article.facade

import com.example.yourssuassignment.application.errorhandling.exception.DeleteUnauthorizedException
import com.example.yourssuassignment.application.errorhandling.exception.UpdateUnauthorizedException
import com.example.yourssuassignment.common.util.PasswordEncryptionUtil
import com.example.yourssuassignment.domain.user.service.ArticleService
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.ArticleDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleFacade(
    private val userService: UserService,
    private val articleService: ArticleService,
) {
    @Transactional
    fun createArticle(
        email: String,
        title: String,
        content: String,
    ): ArticleDto {
        println(email)

        val user = userService.getByEmail(
            email = email,
        )

        return articleService.createArticle(
            email = email,
            title = title,
            content = content,
            user = user,
        ).let { // Article -> ArticleDto
            ArticleDto(
                articleId = it.id,
                email = it.user.email,
                title = it.title,
                content = it.title,
            )
        }
    }

    @Transactional
    fun updateArticle(
        articleId: Long,
        email: String,
        title: String,
        content: String,
    ): ArticleDto {
        val user = userService.getByEmail(
            email = email,
        )

        val article = articleService.getById(
            articleId = articleId,
        )

        // 유저 권한 검증
        if (article.user.id != user.id) throw UpdateUnauthorizedException()

        article.title = title
        article.content = content

        // Aticle -> ArticleDto
        return articleService.save(article)
            .let {
                ArticleDto(
                    articleId = it.id,
                    email = it.user.email,
                    title = it.title,
                    content = it.content,
                )
            }
    }

    @Transactional
    fun deleteArticle(
        articleId: Long,
        email: String,
    ) {
        val user = userService.getByEmail(
            email = email,
        )

        val article = articleService.getById(
            articleId = articleId,
        )

        // 유저 권한 검증
        if (article.user.id != user.id) throw DeleteUnauthorizedException()

        articleService.delete(article)
    }
}
