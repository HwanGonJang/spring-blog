package com.example.yourssuassignment.application.errorhandling

import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.base.ResponseStatusReasonException
import mu.KotlinLogging
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.cloud.sleuth.Tracer
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

/*
Error response 설정
"timestamp": "2022-10-06T08:39:17.320+00:00",
"status": 409,
"error": "Conflict",
"message": "이미 존재하는 고객입니다.",
"path": "/users",
"traceId": "bf968319dadb1bba",
"reason": "USER_ALREADY_EXIST"
*/
@Component
class CustomErrorAttributes(
    private val tracer: Tracer,
) : DefaultErrorAttributes() {
    private val logger = KotlinLogging.logger {}

    override fun getErrorAttributes(
        webRequest: WebRequest,
        options: ErrorAttributeOptions,
    ): Map<String, Any> {
        val errorAttributes = super.getErrorAttributes(webRequest, options)

        tracer.currentSpan()?.let {
            errorAttributes["traceId"] = it.context().traceId()
        }

        val error = getError(webRequest)
        if (error is ResponseStatusReasonException)
            errorAttributes["reason"] = error.exceptionReason

        logger.error(errorAttributes.toString())

        return errorAttributes
    }
}
