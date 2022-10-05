package com.example.yourssuassignment.application.errorhandling

import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.base.ResponseStatusReasonException
import mu.KotlinLogging
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.cloud.sleuth.Tracer
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

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
