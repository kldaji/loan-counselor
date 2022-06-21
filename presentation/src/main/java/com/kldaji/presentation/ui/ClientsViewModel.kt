package com.kldaji.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kldaji.domain.Client
import com.kldaji.presentation.R
import com.kldaji.presentation.ui.clients.entity.ScheduledClientView

class ClientsViewModel : ViewModel() {
    val scheduledClientViews = listOf(
        ScheduledClientView("미팅 예정 고객",
            "오늘의 미팅 예정 고객 명단 입니다!",
            R.drawable.ic_meeting_24),
        ScheduledClientView("대출실행 예정 고객",
            "30일내 대출실행 예정 고객 명단 입니다!",
            R.drawable.ic_action_24)
    )

    val genders = listOf("남자", "여자", "기타")

    private val _clients = MutableLiveData<List<Client>>()
    val clients: LiveData<List<Client>> = _clients

    fun fetchClients() {
        _clients.value = listOf(
            Client(1, "김영욱", "97.07.03"),
            Client(2, "김영욱", "97.07.03"),
            Client(3, "김영욱", "97.07.03"),
            Client(4, "김영욱", "97.07.03"),
            Client(5, "김영욱", "97.07.03"),
            Client(6, "김영욱", "97.07.03"),
            Client(7, "김영욱", "97.07.03"),
            Client(8, "김영욱", "97.07.03"),
            Client(9, "김영욱", "97.07.03"),
            Client(10, "김영욱", "97.07.03"),
            Client(11, "김영욱", "97.07.03")
        )
    }
}
