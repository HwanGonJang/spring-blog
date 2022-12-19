package com.example.yourssuassignment.domain.user.graphql

import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.facade.UserFacade
import com.example.yourssuassignment.dto.UserInquiryDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserQueryMapper(
    private val userFacade: UserFacade,
) {
    @QueryMapping
    fun listUsersByInquiry(
        @Argument
        inquiry: UserInquiryDto
    ): List<User> = userFacade.listByInquiry(
        email = inquiry.email,
        username = inquiry.username,
        createdAtStart = inquiry.createdAtStart?.atStartOfDay(),
        createdAtEnd = inquiry.createdAtEnd?.atStartOfDay(),
        updatedAtStart = inquiry.updatedAtStart?.atStartOfDay(),
        updatedAtEnd = inquiry.updatedAtEnd?.atStartOfDay(),
    )
}
