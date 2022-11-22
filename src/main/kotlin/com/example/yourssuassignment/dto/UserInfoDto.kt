package com.example.yourssuassignment.dto

import com.example.yourssuassignment.common.enums.UserRole
import java.time.LocalDateTime

data class UserInfoDto(
    val id: Long,
    val email: String,
    val username: String,
    val role: UserRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
