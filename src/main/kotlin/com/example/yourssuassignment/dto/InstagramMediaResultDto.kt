package com.example.yourssuassignment.dto

import java.time.LocalDateTime

data class InstagramMediaResultDto(
    val id: String,
    val username: String?,
    val timestamp: LocalDateTime?,
    val media_type: String?,
    val media_urls: List<String?>,
    val permalink: String?,
    val caption: String?,
)
