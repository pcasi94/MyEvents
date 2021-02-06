package com.mimoupsa.myevents.domain.mappers

interface BaseMapper<U, M> {

    fun map(unmappedList: List<U>): List<M>

    fun map(unmapped: U): M
}
