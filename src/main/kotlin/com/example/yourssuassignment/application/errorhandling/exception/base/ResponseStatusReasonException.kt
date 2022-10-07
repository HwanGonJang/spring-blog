package kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.base

import org.springframework.web.server.ResponseStatusException

// Exception abstract class
abstract class ResponseStatusReasonException(val exceptionReason: ExceptionReason) :
    ResponseStatusException(exceptionReason.status, exceptionReason.reason)
