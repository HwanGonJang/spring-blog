package com.example.yourssuassignment.domain.user.controller

import com.example.yourssuassignment.domain.user.facade.UserFacade
import com.example.yourssuassignment.dto.UserDto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserControllerTest : BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var userFacade: UserFacade

    init {
        // POST /users 고객 회원가입 테스트
        Given("the client try to sign up") {
            When("the client calls POST /users") {
                val result = userFacade.createUser(
                    email = "test@gmail.com",
                    password = "1234",
                    username = "tester"
                )
                Then("client's information is saved successfully") {
                    result shouldBe UserDto(
                        email = "test@gmail.com",
                        username = "tester",
                    )
                    println("고객이 정상적으로 등록됐습니다.")
                }
            }
        }

        // DELETE /users 고객 회원탈퇴 테스트
        Given("the client try to withdrawal") {
            When("the client calls DELETE /users") {
                userFacade.deleteUser(
                    email = "test@gmail.com",
                    password = "1234",
                )
                Then("client's information is deleted successfully") {
                    println("고객이 정상적으로 삭제됐습니다.")
                }
            }
        }
    }
}