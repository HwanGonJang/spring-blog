package com.example.yourssuassignment.domain.user.entity

import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
class User (
    @Id
    @Column(name = "user_id", nullable = false)
    val id: Int,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "username", nullable = false)
    val username: String,
)