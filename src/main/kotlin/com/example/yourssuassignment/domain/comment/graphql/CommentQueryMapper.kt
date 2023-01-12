package com.example.yourssuassignment.domain.comment.graphql

import com.example.yourssuassignment.domain.comment.entity.Comment
import com.example.yourssuassignment.domain.comment.facade.CommentFacade
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CommentQueryMapper(
    private val commentFacade: CommentFacade,
) {
    @QueryMapping
    fun listCommentsByArticleId(
        @Argument
        articleId: Long
    ): List<Comment> = commentFacade.listByArticle(articleId)
}
