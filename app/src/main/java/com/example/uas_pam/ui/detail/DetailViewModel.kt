package com.example.uas_pam.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.ImtRepository
import com.example.uas_pam.ui.AllData
import com.example.uas_pam.ui.DetailUIState
import com.example.uas_pam.ui.toAllDataUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val imtRepository: ImtRepository
): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val userId: String = checkNotNull(savedStateHandle[DetailDestination.imtId])


    val dataBasedOnUserId: Flow<AllData> = imtRepository.getAllBasedOnUserId(userId)
        .map { result ->
            requireNotNull(result) { "Result cannot be null" }
        }
        .map { (imt, user) ->
            AllData(
                idData = imt.idData,
                namaUser = user?.namaUser ?: "",
                jeniskUser = user?.jeniskUser ?: "",
                umurUser = user?.umurUser ?: "",
                bbUser = imt.bbUser,
                tbUser = imt.tbUser,
                imtClass = imt.imtClass
            )
        }
        .flowOn(Dispatchers.IO)

    val uiState: StateFlow<DetailUIState> = dataBasedOnUserId
        .filterNotNull()
        .map {
            DetailUIState(allDataUi = it.toAllDataUi())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DetailUIState()
        )

    suspend fun deleteKontak() {
        imtRepository.deleteBasedOnUserId(userId)

    }
}