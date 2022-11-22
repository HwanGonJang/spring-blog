package com.example.yourssuassignment.domain.article.controller

import com.example.yourssuassignment.domain.article.facade.ArticleFacade
import com.example.yourssuassignment.domain.user.facade.UserFacade
import com.example.yourssuassignment.dto.ArticleDto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ArticleControllerTest : BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var userFacade: UserFacade

    @Autowired
    private lateinit var articleFacade: ArticleFacade

    init {
        // POST /articles 게시글 등록 테스트
        Given("the client try to upload article") {
            userFacade.createUser(
                email = "testArticle@gmail.com",
                password = "1234",
                username = "tester"
            )
            When("the client calls POST /articles") {
                val result = articleFacade.createArticle(
                    email = "testArticle@gmail.com",
                    password = "1234",
                    title = "test",
                    content = "test",
                )
                Then("article is saved successfully") {
                    result shouldBe ArticleDto(
                        articleId = 1,
                        email = "testArticle@gmail.com",
                        title = "test",
                        content = "test",
                    )
                    println("게시글이 정상적으로 등록됐습니다.")
                }
            }
        }

        // PATCH /articles/{articleId} 게시글 수정 테스트
        Given("the client try fix article") {
            When("the client calls PATCH /articles/{articleId}") {
                val result = articleFacade.updateArticle(
                    articleId = 1,
                    email = "testArticle@gmail.com",
                    password = "1234",
                    title = "modified test",
                    content = "modified test",
                )
                Then("client's information is fixed successfully") {
                    result shouldBe ArticleDto(
                        articleId = 1,
                        email = "testArticle@gmail.com",
                        title = "modified test",
                        content = "modified test",
                    )
                    println("게시글이 정상적으로 수정됐습니다.")
                }
            }
        }

        // DELETE /articles/{articleId} 게시글 삭제 테스트
        Given("the client try to delete article") {
            When("the client calls DELETE /articles/{articleId}") {
                articleFacade.deleteArticle(
                    articleId = 1,
                    email = "testArticle@gmail.com",
                    password = "1234",
                )
                Then("client's article is deleted successfully") {
                    println("게시글이 정상적으로 삭제됐습니다.")
                }
            }
        }
    }
}
