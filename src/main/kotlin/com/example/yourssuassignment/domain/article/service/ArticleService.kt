package com.example.yourssuassignment.domain.user.service

import com.example.yourssuassignment.domain.application.errorhandling.exception.UserNotFoundException
import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.user.repository.ArticleRepository
import com.example.yourssuassignment.domain.user.repository.UserRepository
import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.PasswordIncorrectException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class ArticleService(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) {
    @Transactional
    fun createArticle(
        email: String,
        password: String,
        title: String,
        content: String,
    ): Article {
        val user = userRepository.findByEmail(
            email = email,
        ) ?: throw UserNotFoundException()

        val passwordEncoder = BCryptPasswordEncoder()
        if (!passwordEncoder.matches(password, user.password))
            throw PasswordIncorrectException()

        val article = Article(
            id = 0,     // auto_increment
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            content = content,
            title = title,
            user = user,
        )

        return articleRepository.save(article)
    }
}