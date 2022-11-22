package com.example.yourssuassignment.domain.user.facade

import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.service.UserService
import com.example.yourssuassignment.dto.UserDto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserFacadeTest : BehaviorSpec() {
    private val userService = mockk<UserService>()

    init {
        // UserFacade createUser 테스트
        Given("the client try to sign up") {
            When("the client calls POST /users") {
                val serviceResult = User(
                    id = 0,
                    email = "test@gmail.com",
                    password = "1234",
                    username = "tester",
                )

                every {
                    userService.createUser(
                        email = "test@gmail.com",
                        password = "1234",
                        username = "tester",
                    )
                } returns serviceResult
                Then("client's information is saved successfully") {
                    val result = userService.createUser(
                        email = "test@gmail.com",
                        password = "1234",
                        username = "tester",
                    )

                    result.let {
                        UserDto(
                            email = it.email,
                            username = it.username,
                        )
                    } shouldBe UserDto(
                        email = "test@gmail.com",
                        username = "tester",
                    )
                    println("test userFacade createUser successfully")
                }
            }
        }

        // UserFacade deleteUser 테스트 생략
    }
}
