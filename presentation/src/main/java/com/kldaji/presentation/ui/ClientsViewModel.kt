package com.kldaji.presentation.ui

import androidx.lifecycle.ViewModel
import com.kldaji.presentation.R
import com.kldaji.presentation.ui.clients.entity.ScheduledClientView

class ClientsViewModel: ViewModel() {
    val scheduledClientViews = listOf(
        ScheduledClientView("미팅 예정 고객",
            "오늘의 미팅 예정 고객 명단 입니다!",
            R.drawable.ic_meeting_24),
        ScheduledClientView("대출실행 예정 고객",
            "30일내 대출실행 예정 고객 명단 입니다!",
            R.drawable.ic_action_24)
    )
}
