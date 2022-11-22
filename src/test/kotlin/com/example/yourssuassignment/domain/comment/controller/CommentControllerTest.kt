package com.example.yourssuassignment.domain.comment.controller

import com.example.yourssuassignment.domain.article.facade.ArticleFacade
import com.example.yourssuassignment.domain.comment.facade.CommentFacade
import com.example.yourssuassignment.domain.user.facade.UserFacade
import com.example.yourssuassignment.dto.CommentDto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentControllerTest : BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var userFacade: UserFacade

    @Autowired
    private lateinit var articleFacade: ArticleFacade

    @Autowired
    private lateinit var commentFacade: CommentFacade

    init {
        // POST /comments/{articleId} 댓글 등록 테스트
        Given("the client try to upload comment") {
            userFacade.createUser(
                email = "testComment@gmail.com",
                password = "1234",
                username = "tester"
            )
            articleFacade.createArticle(
                email = "testComment@gmail.com",
                password = "1234",
                title = "test",
                content = "test",
            )
            When("the client calls POST /comments/{articleId}") {
                val result = commentFacade.createComment(
                    articleId = 2,
                    email = "testComment@gmail.com",
                    password = "1234",
                    content = "test",
                )
                Then("comment is saved successfully") {
                    result shouldBe CommentDto(
                        commentId = 1,
                        email = "testComment@gmail.com",
                        content = "test",
                    )
                    println("댓글이 정상적으로 등록됐습니다.")
                }
            }
        }

        // PATCH /comments/{articleId}/{commentId} 댓글 수정 테스트
        Given("the client try fix comment") {
            When("the client calls PATCH /comments/{articleId}/{commentId}") {
                val result = commentFacade.updateComment(
                    articleId = 2,
                    commentId = 1,
                    email = "testComment@gmail.com",
                    password = "1234",
                    content = "modified test",
                )
                Then("client's information is fixed successfully") {
                    result shouldBe CommentDto(
                        commentId = 1,
                        email = "testComment@gmail.com",
                        content = "modified test",
                    )
                    println("댓글이 정상적으로 수정됐습니다.")
                }
            }
        }

        // DELETE /articles/{articleId} 게시글 삭제 테스트
        Given("the client try to delete article") {
            When("the client calls DELETE /articles/{articleId}") {
                commentFacade.deleteComment(
                    articleId = 2,
                    commentId = 1,
                    email = "testComment@gmail.com",
                    password = "1234",
                )
                Then("client's article is deleted successfully") {
                    println("댓글이 정상적으로 삭제됐습니다.")
                }
            }
        }
    }
}
