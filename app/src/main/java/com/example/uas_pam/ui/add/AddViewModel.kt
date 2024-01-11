package com.example.uas_pam.ui.add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.uas_pam.data.ImtRepository
import com.example.uas_pam.data.UserRepository
import com.example.uas_pam.ui.DetailImt
import com.example.uas_pam.ui.DetailUser
import com.example.uas_pam.ui.UserUIState
import com.example.uas_pam.ui.ImtUIState
import com.example.uas_pam.ui.toImt
import com.example.uas_pam.ui.toUser

class AddViewModel(private val userRepository: UserRepository, private val imtRepository: ImtRepository): ViewModel(){
    var uiStateUser by mutableStateOf(UserUIState())
    var uiStateImt by mutableStateOf(ImtUIState())
    private var userID : String = ""


    fun updateUiStateUser(detailUser: DetailUser){
        uiStateUser = UserUIState(detailUser = detailUser)
    }

    fun updateUiStateImt(detailImt: DetailImt){
        uiStateImt = ImtUIState(detailImt = detailImt)
    }
    suspend fun saveUser() {
        userID = userRepository.save(uiStateUser.detailUser.toUser())
    }

    suspend fun saveImt(){
        val imtValue = uiStateImt.detailImt.determineImt()
        val imt = uiStateImt.detailImt.toImt().apply {
            imtClass = imtValue
            idUser = userID
        }
        imtRepository.save(imt)

    }


}