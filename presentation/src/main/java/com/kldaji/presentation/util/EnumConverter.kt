package com.kldaji.presentation.util

import com.kldaji.domain.Gender
import com.kldaji.domain.Loan
import com.kldaji.presentation.R

object EnumConverter {
    fun stringToLoan(checkedRadioButtonId: Int): Loan {
        return when (checkedRadioButtonId) {
            R.id.rb_dambo -> Loan.DAMBO
            else -> Loan.JEONSE
        }
    }

    fun stringToGender(gender: String): Gender {
        return when (gender) {
            "남자" -> Gender.MALE
            "여자" -> Gender.FEMALE
            else -> Gender.ETC
        }
    }
}
