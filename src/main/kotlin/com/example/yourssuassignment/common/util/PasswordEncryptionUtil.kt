package com.example.yourssuassignment.common.util

import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.PasswordIncorrectException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object PasswordEncryptionUtil {
    fun encrypt(password: String): String {
        val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
        return encoder.encode(password)
    }

    fun isEqualToEncryptedPassword(
        password: String,
        encryptedPassword: String,
    ): Boolean {
        val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
        if (!encoder.matches(password, encryptedPassword))
            throw PasswordIncorrectException()

        return true
    }
}