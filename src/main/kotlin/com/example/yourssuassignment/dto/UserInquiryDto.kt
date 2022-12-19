package com.example.yourssuassignment.dto

import java.time.LocalDate

data class UserInquiryDto(
    val email: String? = null,
    val username: String? = null,
    val createdAtStart: LocalDate? = null,
    val createdAtEnd: LocalDate? = null,
    val updatedAtStart: LocalDate? = null,
    val updatedAtEnd: LocalDate? = null,
)
