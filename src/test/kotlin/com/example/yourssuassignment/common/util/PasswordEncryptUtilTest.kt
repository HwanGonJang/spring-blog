package com.example.yourssuassignment.common.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith
import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.PasswordIncorrectException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordEncryptUtilTest : BehaviorSpec() {
    init {
        // PasswordEncryptUtil encrypt 테스트
        Given("the client try to sign up") {
            When("controller get user password to sign up") {
                val password = "1234"
                Then("encrypt user password") {
                    val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
                    val encryptedPassword = encoder.encode(password)

                    encoder.matches(password, encryptedPassword) shouldBe true
                    println("비밀번호가 정상적으로 암호화됐습니다.")
                }
            }
        }

        // PasswordEncryptUtil isEqualToEncryptedPassword 테스트
        Given("the client try to use specific services with correct password") {
            val password = "1234"
            val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
            val encryptedPassword = encoder.encode(password)
            When("service needs to verify user") {
                val correctPassword = "1234"
                Then("check user password") {
                    encoder.matches(correctPassword, encryptedPassword) shouldBe true
                    println("비밀번호가 일치합니다.")
                }
            }

            // 비밀번호가 일치하지 않을 때
            When("service needs to verify user with incorrect password") {
                val wrongPassword = "wrongpw"
                Then("check user password") {
                    val exception = shouldThrow<PasswordIncorrectException> {
                        if (!encoder.matches(
                                wrongPassword,
                                encryptedPassword
                            )
                        ) throw PasswordIncorrectException()
                    }
                    exception.message should startWith("406")
                    println("비밀번호가 일치하지 않습니다.")
                }
            }
        }
    }
}