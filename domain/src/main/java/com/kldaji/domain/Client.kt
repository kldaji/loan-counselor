package com.kldaji.domain

import java.io.Serializable

data class Client(
    val id: Long,
    val name: String,
    val phone: String,
    val birth: String,
    val loan: Loan,
    val gender: Gender,
    val meeting: Long,
    val run: Long,
    val remark: String,
) : Serializable
