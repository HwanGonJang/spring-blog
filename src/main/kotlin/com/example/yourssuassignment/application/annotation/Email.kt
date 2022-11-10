package com.example.yourssuassignment.application.annotation

import io.swagger.v3.oas.annotations.Parameter

@Target(AnnotationTarget.VALUE_PARAMETER)
@Parameter(hidden = true)
annotation class Email
