package com.example.yourssuassignment.domain.article.graphql

import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.article.facade.ArticleFacade
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class ArticleQueryMapper(
    private val articleFacade: ArticleFacade,
) {
    @QueryMapping
    fun listArticlesByUserId(
        @Argument
        userId: Long
    ): List<Article> = articleFacade.listByUserId(userId)
}
