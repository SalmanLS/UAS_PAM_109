package com.example.uas_pam.ui.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.ImtRepository
import com.example.uas_pam.data.UserRepository
import com.example.uas_pam.ui.DetailImt
import com.example.uas_pam.ui.DetailUser
import com.example.uas_pam.ui.ImtUIState
import com.example.uas_pam.ui.UserUIState
import com.example.uas_pam.ui.toImt
import com.example.uas_pam.ui.toImtUIState
import com.example.uas_pam.ui.toUser
import com.example.uas_pam.ui.toUserUIState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val imtRepository: ImtRepository,
    private val userRepository: UserRepository
): ViewModel() {

    var userUiState by mutableStateOf(UserUIState())
        private set
    var imtUiState by mutableStateOf(ImtUIState())
        private set

    private val userId: String = checkNotNull(savedStateHandle[EditDestination.imtId])
    init {
        viewModelScope.launch {
            userUiState = userRepository.getUserById(userId).filterNotNull().first().toUserUIState()
        }
    }
    init {
        viewModelScope.launch {
            imtUiState = imtRepository.getImtByUserId(userId).filterNotNull().first().toImtUIState()
        }
    }

    fun updateUiStateUser(detailUser: DetailUser){
        userUiState = userUiState.copy(detailUser = detailUser)
    }

    fun updateUiStateImt(detailImt: DetailImt){
        imtUiState = imtUiState.copy(detailImt = detailImt)
    }

    suspend fun updateUser() {
        userRepository.update(userUiState.detailUser.toUser())

    }

    suspend fun updateImt(){
        val imtValue = imtUiState.detailImt.determineImt()
        val imt = imtUiState.detailImt.toImt().apply {
            imtClass = imtValue
        }
        imtRepository.update(imt)
    }

}