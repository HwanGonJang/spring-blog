package com.example.yourssuassignment.dto

import com.example.yourssuassignment.common.enum.UserRole

data class UserDto(
    val email: String,
    val username: String,
    val role: UserRole,
)
