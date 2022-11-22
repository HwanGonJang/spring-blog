package com.example.yourssuassignment.domain.user.repository

import com.example.yourssuassignment.domain.user.entity.User
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

// 기본 JPA 메소드는 테스트에서 제외
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository
) : BehaviorSpec() {
    init {
        // UserRepository findByEmail 테스트
        Given("the client try to use service and specific user is saved in db") {
            val email = "test@gmail.com"
            val user = userRepository.save(
                User(
                    id = 1,
                    email = email,
                    password = "1234",
                    username = "tester",
                )
            )
            When("the client calls api that needs the user") {
                val result = userRepository.findByEmail(
                    email = email,
                )
                Then("find user for validation or info") {
                    result?.id shouldBe user.id
                    result?.email shouldBe user.email
                    result?.password shouldBe user.password
                    result?.username shouldBe user.username

                    println("고객이 정상적으로 조회됐습니다.")
                }
            }
        }
    }
}
