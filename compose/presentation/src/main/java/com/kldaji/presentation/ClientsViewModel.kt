package com.kldaji.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kldaji.domain.Client
import com.kldaji.domain.usecase.DeleteClientUseCase
import com.kldaji.domain.usecase.GetAllClientsUseCase
import com.kldaji.domain.usecase.InsertClientUseCase
import com.kldaji.domain.usecase.UpdateClientUseCase
import com.kldaji.presentation.util.CalendarLogic
import com.kldaji.presentation.util.DateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getAllClientsUseCase: GetAllClientsUseCase,
    private val insertClientUseCase: InsertClientUseCase,
    private val updateClientUseCase: UpdateClientUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
) : ViewModel() {

    private val _clients = MutableLiveData<List<Client>>()
    val clients: LiveData<List<Client>> = _clients

    private val _clientsByName = MutableLiveData<List<Client>>()
    val clientsByName: LiveData<List<Client>> = _clientsByName

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

    private val _pictures = MutableLiveData<List<String>>(listOf())
    val pictures: LiveData<List<String>> = _pictures

    fun fetchPictures(client: Client) {
        _pictures.value = client.pictures
    }

    fun setPicturesEmpty() {
        _pictures.value = listOf()
    }

    fun addPicture(uri: String?) {
        uri ?: return

        val tempPictures = _pictures.value?.toMutableList() ?: return
        tempPictures.add(uri)
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
        val startOfTodayTimestamp = CalendarLogic.getStartOfTodayTimestamp()
        if (index == 0) { // meeting
            val meetingClients = clients.value?.filter {
                val endOfTodayTimeStamp = CalendarLogic.getEndOfTodayTimestamp()
                it.meeting in startOfTodayTimestamp..endOfTodayTimeStamp
            } ?: listOf()
            _scheduledClients.value = meetingClients.sortedBy { it.meetingTime }
        } else { // run
            val runClients = clients.value?.filter {
                val afterOneMonthTimestamp = CalendarLogic.getAfterOneMonthTimestamp()
                it.run in startOfTodayTimestamp..afterOneMonthTimestamp
            } ?: listOf()
            _scheduledClients.value = runClients.sortedBy { it.run }
        }
    }

    fun getMeetingClientsInMonth(dates: List<Date>): List<List<Client>> {
        val meetingClients = mutableListOf<List<Client>>()
        dates.forEach { date ->
            meetingClients.add(getMeetingClients(date))
        }
        return meetingClients
    }

    fun getRunClientsInMonth(dates: List<Date>): List<List<Client>> {
        val runClients = mutableListOf<List<Client>>()
        dates.forEach { date ->
            runClients.add(getRunClients(date))
        }
        return runClients
    }

    fun getMeetingClients(date: Date): List<Client> {
        return _clients.value?.filter { DateConverter.dateToLong(date) == it.meeting } ?: listOf()
    }

    fun getRunClients(date: Date): List<Client> {
        return _clients.value?.filter { DateConverter.dateToLong(date) == it.run } ?: listOf()
    }

    fun getClientsByName(name: String) {
        if (name == "") return
        val currentClients = _clients.value ?: return

        _clientsByName.value = currentClients.filter { it.name.contains(name) }
    }

    fun clearClientsByName() {
        _clientsByName.value = listOf()
    }
}
