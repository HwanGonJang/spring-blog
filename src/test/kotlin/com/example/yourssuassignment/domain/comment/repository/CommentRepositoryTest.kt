package com.example.yourssuassignment.domain.comment.repository

import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.comment.entity.Comment
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.repository.ArticleRepository
import com.example.yourssuassignment.domain.user.repository.UserRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

// 기본 JPA 메소드는 테스트에서 제외
@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository
) : BehaviorSpec() {
    init {
        // CommentRepository findByIdAndArticleId 테스트
        Given("the client try to use service and specific user is saved in db") {
            val user = userRepository.save(
                User(
                    id = 1,
                    email = "test@gmail.com",
                    password = "1234",
                    username = "tester",
                )
            )

            val article = articleRepository.save(
                Article(
                    id = 1,
                    content = "test",
                    title = "test",
                    user = user,
                )
            )

            val comment = commentRepository.save(
                Comment(
                    id = 1,
                    content = "test",
                    article = article,
                    user = user,
                )
            )
            When("the client calls api that needs the user") {
                val result = commentRepository.findByIdAndArticleId(
                    articleId = 1,
                    commentId = 1,
                )
                Then("find user for validation or info") {
                    result?.id shouldBe comment.id
                    result?.content shouldBe comment.content

                    println("댓글이 정상적으로 조회됐습니다.")
                }
            }
        }
    }
}