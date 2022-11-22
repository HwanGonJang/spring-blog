package com.example.yourssuassignment.common.util

import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.PasswordIncorrectException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

// 유저 검증 Util
object PasswordEncryptionUtil {
    // 패스워드 BCryptEncryption으로 암호화
    fun encrypt(password: String): String {
        val encoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
        return encoder.encode(password)
    }

    // 유저 패스워드 검증
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
