package com.kldaji.domain

import java.io.Serializable

data class Client(
    val id: Long = 0L, // auto generated
    val name: String = "",
    val phone: String = "",
    val birth: String = "",
    val loan: Loan = Loan.JEONSE,
    val gender: Gender = Gender.MALE,
    val meeting: Long = 0L,
    val meetingTime: String = "",
    val run: Long = 0L,
    val remark: String = "",
    val pictures: List<String> = listOf()
) : Serializable

