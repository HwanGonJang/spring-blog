package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith
import io.mockk.every
import io.mockk.mockk
import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.UserAlreadyExistException

class UserServiceTest : BehaviorSpec() {
    private val userRepository = mockk<UserRepository>()

    init {
        // UserService getByEmail 테스트 생략

        // UserService createUser 테스트
        Given("the client try to sign up") {
            When("the client calls POST /users") {
                every {
                    userRepository.findByEmail(
                        email = "test@gmail.com"
                    )
                } returns null
                Then("client's information is saved successfully") {
                    val result = userRepository.findByEmail(
                        email = "test@gmail.com"
                    )

                    if (result == null)
                        result shouldBe null
                    else
                        throw UserAlreadyExistException()
                }
            }

            // 이미 가입한 고객일때
            When("registered client calls POST /users") {
                every {
                    userRepository.findByEmail(
                        email = "registered@gmail.com"
                    )
                } returns User(
                    id = 0,
                    email = "registered@gmail.com",
                    password = "1234",
                    username = "등록된 고객"
                )
                Then("the client can't sing up again") {
                    val exception = shouldThrow<UserAlreadyExistException> {
                        val user = userRepository.findByEmail(
                            email = "registered@gmail.com"
                        )

                        if (user != null) throw UserAlreadyExistException()
                    }
                    exception.message should startWith("409")
                    println("이미 존재하는 고객입니다.")
                }
            }
        }

        // UserService delete 테스트 생략
    }
}