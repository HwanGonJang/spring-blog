package kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.base

import kr.smartdoctor.api.autoreceipt.dto.ExceptionReasonDto
import org.springframework.http.HttpStatus

enum class ExceptionReason(val status: HttpStatus, val reason: String) {
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 고객입니다."),
    ;

    fun toDto() = ExceptionReasonDto(
        name = name,
        status = status.name,
        statusCode = status.value(),
        reason = reason,
    )
}
