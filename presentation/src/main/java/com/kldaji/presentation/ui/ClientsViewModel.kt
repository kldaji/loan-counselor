package com.kldaji.presentation.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.domain.Client
import com.kldaji.domain.GetAllClientsUseCase
import com.kldaji.domain.InsertClientUseCase
import com.kldaji.presentation.R
import com.kldaji.presentation.ui.clients.entity.ScheduledClientView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getAllClientsUseCase: GetAllClientsUseCase,
    private val insertClientUseCase: InsertClientUseCase,
) : ViewModel() {
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
        viewModelScope.launch(Dispatchers.IO) {
            _clients.postValue(getAllClientsUseCase())
        }
    }

    fun insertClient(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            insertClientUseCase(client)
            // or do insertion sort to avoid fetch all clients overhead
            fetchClients()
        }
    }

    private val _pictures = MutableLiveData<List<String>>()
    val pictures: LiveData<List<String>> = _pictures

    fun fetchPictures(client: Client?) {
        _pictures.value = client?.pictures ?: listOf()
    }

    fun addPicture(uri: Uri?) {
        uri ?: return

        val tempPictures = _pictures.value?.toMutableList() ?: return
        tempPictures.add(uri.toString())
        _pictures.value = tempPictures
    }

    fun deletePicture(uri: String) {
        val tempPictures = _pictures.value?.toMutableList() ?: return
        tempPictures.remove(uri)
        _pictures.value = tempPictures
    }
}
