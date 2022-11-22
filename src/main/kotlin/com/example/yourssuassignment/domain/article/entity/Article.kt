package com.example.yourssuassignment.domain.article.entity

import com.example.yourssuassignment.common.entity.BaseTimeEntity
import com.example.yourssuassignment.domain.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "article")
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false)
    val id: Long,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "title", nullable = false)
    var title: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseTimeEntity()
