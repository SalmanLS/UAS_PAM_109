package com.example.uas_pam.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.ImtRepository
import com.example.uas_pam.model.Imt
import com.example.uas_pam.ui.allData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


sealed class ImtUIState{
    data class Success(val user: Flow<List<Imt>>): ImtUIState()
    object Error : ImtUIState()

    object Loading : ImtUIState()
}
class HomeViewModel(private val imtRepository: ImtRepository): ViewModel() {
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUIState : StateFlow<allData> = imtRepository.getAllWithUser()
        .filterNotNull()
        .map { allData(alldata = it.toList(),it.size) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = allData()
        )

}