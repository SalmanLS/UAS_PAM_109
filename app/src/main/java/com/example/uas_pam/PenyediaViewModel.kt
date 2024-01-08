package com.example.uas_pam


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.ui.add.AddViewModel

fun CreationExtras.imtApplication(): ImtApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ImtApplication)
object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            AddViewModel(
                imtApplication().container.userRepository,
                imtApplication().container.imtRepository
            )
        }
    }
}