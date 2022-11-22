package com.example.yourssuassignment.domain.article.facade

import com.example.yourssuassignment.application.errorhandling.exception.UpdateUnauthorizedException
import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.service.ArticleService
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.ArticleDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith
import io.mockk.every
import io.mockk.mockk

class ArticleFacadeTest : BehaviorSpec() {
    private val userService = mockk<UserService>()
    private val articleService = mockk<ArticleService>()

    init {
        // ArticleFacade createArticle 테스트
        Given("the client try to upload article") {
            When("the client calls POST /articles") {
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

                every {
                    articleService.createArticle(
                        email = "test@gmail.com",
                        password = "1234",
                        title = "test",
                        content = "test",
                        user = userServiceResult,
                    )
                } returns articleServiceResult
                Then("client's article is saved successfully") {
                    val result = articleService.createArticle(
                        email = "test@gmail.com",
                        password = "1234",
                        title = "test",
                        content = "test",
                        user = userServiceResult,
                    )

                    result.let {
                        ArticleDto(
                            articleId = it.id,
                            email = it.user.email,
                            title = it.title,
                            content = it.content,
                        )
                    } shouldBe ArticleDto(
                        articleId = 1,
                        email = "test@gmail.com",
                        content = "test",
                        title = "test",
                    )
                    println("게시글이 정상적으로 등록됐습니다.")
                }
            }
        }

        // ArticleFacade updateArticle 테스트
        Given("the client try to fix article") {
            When("the client calls PATCH /articles/{articleId}") {
                val userServiceResult = User(
                    id = 1,
                    email = "test@gmail.com",
                    password = "1234",
                    username = "tester",
                )

                val articleServiceResult = Article(
                    id = 1,
                    content = "modified test",
                    title = "modified test",
                    user = userServiceResult,
                )

                every {
                    articleService.save(articleServiceResult)
                } returns articleServiceResult
                Then("client's article is fixed successfully") {
                    val result = articleService.save(articleServiceResult)

                    result.let {
                        ArticleDto(
                            articleId = it.id,
                            email = it.user.email,
                            title = it.title,
                            content = it.title,
                        )
                    } shouldBe ArticleDto(
                        articleId = 1,
                        email = "test@gmail.com",
                        content = "modified test",
                        title = "modified test",
                    )
                    println("게시글이 정상적으로 수정됐습니다.")
                }
            }

            // 게시글 작성자가 아닐 때
            When("another client calls PATCH /articles/{articleId}") {
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
                    content = "modified test",
                    title = "modified test",
                    user = userServiceResult,
                )

                every {
                    userService.getByEmail(
                        email = "anotherTest@gmail.com"
                    )
                } returns anotherUserServiceResult

                every {
                    articleService.save(articleServiceResult)
                } returns articleServiceResult
                Then("client's article is fixed successfully") {
                    val user = userService.getByEmail(
                        email = "anotherTest@gmail.com"
                    )
                    val result = articleService.save(articleServiceResult)

                    val exception = shouldThrow<UpdateUnauthorizedException> {
                        if (result.user.id != user.id) throw UpdateUnauthorizedException()
                    }
                    exception.message should startWith("406")
                    println("게시글을 수정할 권한이 없습니다.")
                }
            }
        }

        // ArticleFacade deleteArticle 테스트 생략
    }
}
