package com.example.yourssuassignment.domain.user.controller.response

import com.example.yourssuassignment.dto.UserInfoDto

data class GetUserResponse(
    val users: List<UserInfoDto>
)
