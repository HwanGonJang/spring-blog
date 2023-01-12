package com.example.yourssuassignment.domain.user.graphql

import com.example.yourssuassignment.domain.auth.controller.request.CreateUserRequest
import com.example.yourssuassignment.domain.user.entity.User
import com.example.yourssuassignment.domain.user.facade.UserFacade
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class UserMutationMapper(
    private val userFacade: UserFacade,
) {
    @MutationMapping
    fun createUser(
        @Argument
        createUserRequest: CreateUserRequest
    ): User = userFacade.createUserForGql(
        email = createUserRequest.email,
        password = createUserRequest.password,
        username = createUserRequest.username,
        role = createUserRequest.role,
    )
}
