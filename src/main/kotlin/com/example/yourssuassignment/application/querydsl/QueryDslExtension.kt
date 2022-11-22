package com.example.yourssuassignment.application.querydsl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.FilteredClause
import com.querydsl.core.types.Predicate

object QueryDslExtension {
    fun <C: FilteredClause<C>> FilteredClause<C>.and(vararg o: Predicate): C = where(*o)

    fun BooleanBuilder.andIf(predicate: Boolean, then: () -> Predicate): BooleanBuilder =
        if (predicate) this.and(then()) else this
}
