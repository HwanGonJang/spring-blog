package kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.base

import kr.smartdoctor.api.autoreceipt.dto.ExceptionReasonDto
import org.springframework.http.HttpStatus

enum class ExceptionReason(val status: HttpStatus, val reason: String) {
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 고객입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 고객입니다."),
    PASSWORD_INCORRECT(HttpStatus.NOT_ACCEPTABLE, "비밀번호가 일치하지 않습니다."),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
    UPDATE_UNAUTHORIZED(HttpStatus.NOT_ACCEPTABLE, "수정할 권한이 없습니다."),
    DELETE_UNAUTHORIZED(HttpStatus.NOT_ACCEPTABLE, "삭제할 권한이 없습니다."),
    ;

    fun toDto() = ExceptionReasonDto(
        name = name,
        status = status.name,
        statusCode = status.value(),
        reason = reason,
    )
}
