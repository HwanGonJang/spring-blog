package com.example.yourssuassignment.domain.article.repository

import com.example.yourssuassignment.domain.user.repository.ArticleRepository
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

// 기본 JPA 메소드는 테스트에서 제외
@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest @Autowired constructor(
    private val articleRepository: ArticleRepository
) : BehaviorSpec()