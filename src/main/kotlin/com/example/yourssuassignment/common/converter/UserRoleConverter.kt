package com.example.yourssuassignment.common.converter

import com.example.yourssuassignment.common.enum.UserRole
import javax.persistence.AttributeConverter

class UserRoleConverter: AttributeConverter<UserRole, String> {
    override fun convertToDatabaseColumn(attribute: UserRole): String =
        attribute.role

    override fun convertToEntityAttribute(dbData: String): UserRole =
        UserRole.values().first { it.role == dbData }
}