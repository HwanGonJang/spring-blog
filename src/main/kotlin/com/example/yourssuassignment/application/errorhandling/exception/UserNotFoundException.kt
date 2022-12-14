package com.example.yourssuassignment.application.errorhandling.exception

import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.base.ExceptionReason
import kr.smartdoctor.api.autoreceipt.application.errorhandling.exception.base.ResponseStatusReasonException

class UserNotFoundException : ResponseStatusReasonException(ExceptionReason.USER_NOT_FOUND)
