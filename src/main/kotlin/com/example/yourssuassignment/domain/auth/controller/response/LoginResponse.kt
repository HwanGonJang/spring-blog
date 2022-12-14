package com.example.yourssuassignment.domain.auth.controller.response

import com.example.yourssuassignment.common.enums.UserRole

class LoginResponse(
    val email: String,
    val username: String,
    val role: UserRole,
    val accessToken: String,
    val refreshToken: String?,
)
