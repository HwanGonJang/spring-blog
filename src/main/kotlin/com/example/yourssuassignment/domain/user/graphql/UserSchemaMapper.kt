package com.example.yourssuassignment.domain.user.graphql

import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.comment.entity.Comment
import com.example.yourssuassignment.domain.comment.service.CommentService
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.service.ArticleService
import com.example.yourssuassignment.domain.user.service.UserService
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.stereotype.Controller

@Controller
class UserSchemaMapper(
    private val articleService: ArticleService,
    private val commentService: CommentService,
) {
    @BatchMapping(field = "articles", typeName = "User")
    fun getArticleMapByUser(users: List<User>): Map<User, List<Article>> {
        val articlesMap = articleService.listByUsers(users)
            .groupBy { it.user.id }

        return users.associateWith { articlesMap[it.id] ?: listOf() }
    }

    @BatchMapping(field = "comments", typeName = "Article")
    fun getCommentMapByArticle(articles: List<Article>): Map<Article, List<Comment>> {
        val commentsMap = commentService.listByArticles(articles)
            .groupBy { it.article.id }

        return articles.associateWith { commentsMap[it.id] ?: listOf() }
    }
}
