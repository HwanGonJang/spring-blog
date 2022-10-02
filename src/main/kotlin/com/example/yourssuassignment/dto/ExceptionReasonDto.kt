package kr.smartdoctor.api.autoreceipt.dto

data class ExceptionReasonDto(
    val name: String,
    val status: String,
    val statusCode: Int,
    val reason: String,
)

