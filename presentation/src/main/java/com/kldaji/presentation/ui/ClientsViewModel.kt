package com.kldaji.presentation.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.domain.Client
import com.kldaji.domain.usecase.DeleteClientUseCase
import com.kldaji.domain.usecase.GetAllClientsUseCase
import com.kldaji.domain.usecase.InsertClientUseCase
import com.kldaji.domain.usecase.UpdateClientUseCase
import com.kldaji.presentation.R
import com.kldaji.presentation.ui.clients.entity.ScheduledClientView
import com.kldaji.presentation.util.CommonLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getAllClientsUseCase: GetAllClientsUseCase,
    private val insertClientUseCase: InsertClientUseCase,
    private val updateClientUseCase: UpdateClientUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
) : ViewModel() {
    val scheduledClientViews = listOf(
        ScheduledClientView(0, "미팅 예정 고객",
            "오늘의 미팅 예정 고객 명단 입니다!",
            R.drawable.ic_meeting_24),
        ScheduledClientView(1, "대출실행 예정 고객",
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

    fun updateClient(prev: Client, client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            updateClientUseCase(client)
            val temp = _clients.value!!.toMutableList()
            val index = temp.binarySearch(prev, compareBy { it.id })
            temp[index] = client
            _clients.postValue(temp)
        }
    }

    fun deleteClient(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteClientUseCase(client)
            val temp = _clients.value!!.toMutableList()
            val index = temp.binarySearch(client, compareBy { it.id })
            temp.removeAt(index)
            _clients.postValue(temp)
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

    private val _scheduledClients = MutableLiveData<List<Client>>()
    val scheduledClients: LiveData<List<Client>> = _scheduledClients

    fun fetchScheduledClients(index: Int) {
        val startOfTodayTimestamp = CommonLogic.getStartOfTodayTimestamp()
        if (index == 0) { // meeting
            val meetingClients = clients.value?.filter {
            val endOfTodayTimeStamp = CommonLogic.getEndOfTodayTimestamp()
                it.meeting in startOfTodayTimestamp..endOfTodayTimeStamp
            } ?: listOf()
            _scheduledClients.value = meetingClients
        } else { // run
            val runClients = clients.value?.filter {
                val afterOneMonthTimestamp = CommonLogic.getAfterOneMonthTimestamp()
                it.run in startOfTodayTimestamp..afterOneMonthTimestamp
            } ?: listOf()
            _scheduledClients.value = runClients
        }
    }
}
