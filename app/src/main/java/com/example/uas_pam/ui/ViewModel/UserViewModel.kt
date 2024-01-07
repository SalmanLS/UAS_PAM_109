package com.example.uas_pam.ui.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.uas_pam.data.UserRepository
import com.example.uas_pam.ui.UserUIState
import com.example.uas_pam.ui.toUser

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    var uiStateUser by mutableStateOf(UserUIState())

    suspend fun saveUser() {
        userRepository.save(uiStateUser.detailUser.toUser())
    }
}