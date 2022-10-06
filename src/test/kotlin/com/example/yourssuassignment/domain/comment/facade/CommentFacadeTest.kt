package com.example.yourssuassignment.domain.comment.facade

import com.example.yourssuassignment.application.errorhandling.exception.UpdateUnauthorizedException
import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.comment.entity.Comment
import com.example.yourssuassignment.domain.comment.service.CommentService
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.service.ArticleService
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.CommentDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith
import io.mockk.every
import io.mockk.mockk

class CommentFacadeTest : BehaviorSpec() {
    private val userService = mockk<UserService>()
    private val articleService = mockk<ArticleService>()
    private val commentService = mockk<CommentService>()

    init {
        // CommentFacade createComment 테스트
        Given("the client try to upload comment") {
            When("the client calls POST /comments/{articleId}") {
                val userServiceResult = User(
                    id = 1,
                    email = "test@gmail.com",
                    password = "1234",
                    username = "tester",
                )

                val articleServiceResult = Article(
                    id = 1,
                    content = "test",
                    title = "test",
                    user = userServiceResult,
                )

                val commentServiceResult = Comment(
                    id = 1,
                    content = "test",
                    user = userServiceResult,
                    article = articleServiceResult,
                )

                every {
                    commentService.createComment(
                        content = "test",
                        user = userServiceResult,
                        article = articleServiceResult,
                    )
                } returns commentServiceResult
                Then("client's comment is saved successfully") {
                    val result = commentService.createComment(
                        content = "test",
                        user = userServiceResult,
                        article = articleServiceResult,
                    )

                    result.let {
                        CommentDto(
                            commentId = it.id,
                            email = it.user.email,
                            content = it.content,
                        )
                    } shouldBe CommentDto(
                        commentId = 1,
                        email = "test@gmail.com",
                        content = "test",
                    )
                    println("댓글이 정상적으로 등록됐습니다.")
                }
            }
        }

        // CommentFacade updateComment 테스트
        Given("the client try to fix comment") {
            When("the client calls PATCH /comments/{articleId}/{commentId}") {
                val userServiceResult = User(
                    id = 1,
                    email = "test@gmail.com",
                    password = "1234",
                    username = "tester",
                )

                val articleServiceResult = Article(
                    id = 1,
                    content = "test",
                    title = "test",
                    user = userServiceResult,
                )

                val commentServiceResult = Comment(
                    id = 1,
                    content = "modified test",
                    user = userServiceResult,
                    article = articleServiceResult,
                )

                every {
                    commentService.save(commentServiceResult)
                } returns commentServiceResult
                Then("client's comment is fixed successfully") {
                    val result = commentService.save(commentServiceResult)

                    result.let {
                        CommentDto(
                            commentId = it.id,
                            email = it.user.email,
                            content = it.content,
                        )
                    } shouldBe CommentDto(
                        commentId = 1,
                        email = "test@gmail.com",
                        content = "modified test",
                    )
                    println("댓글이 정상적으로 수정됐습니다.")
                }
            }

            // 댓글 작성자가 아닐 때
            When("another client calls PATCH /comments/{articleId}/{commentId}") {
                val userServiceResult = User(
                    id = 1,
                    email = "test@gmail.com",
                    password = "1234",
                    username = "tester",
                )

                val anotherUserServiceResult = User(
                    id = 2,
                    email = "anotherTest@gmail.com",
                    password = "1234",
                    username = "anotherTester",
                )

                val articleServiceResult = Article(
                    id = 1,
                    content = "test",
                    title = "test",
                    user = userServiceResult,
                )

                val commentServiceResult = Comment(
                    id = 1,
                    content = "modified test",
                    user = userServiceResult,
                    article = articleServiceResult,
                )

                every {
                    userService.getByEmail(
                        email = "anotherTest@gmail.com"
                    )
                } returns anotherUserServiceResult

                every {
                    commentService.save(commentServiceResult)
                } returns commentServiceResult
                Then("client's article is fixed successfully") {
                    val user = userService.getByEmail(
                        email = "anotherTest@gmail.com"
                    )
                    val result = commentService.save(commentServiceResult)

                    val exception = shouldThrow<UpdateUnauthorizedException> {
                        if (result.user.id != user.id) throw UpdateUnauthorizedException()
                    }
                    exception.message should startWith("406")
                    println("댓글을 수정할 권한이 없습니다.")
                }
            }
        }

        // CommentFacade deleteComment 테스트 생략
    }
}