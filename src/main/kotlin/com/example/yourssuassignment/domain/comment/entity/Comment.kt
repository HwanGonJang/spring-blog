package com.example.yourssuassignment.domain.comment.entity

import com.example.yourssuassignment.common.entity.BaseTimeEntity
import com.example.yourssuassignment.domain.article.entity.Article
import com.example.yourssuassignment.domain.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "comment")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    val id: Long,

    @Column(name = "content", nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id", nullable = false)
    val article: Article,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
) : BaseTimeEntity()
