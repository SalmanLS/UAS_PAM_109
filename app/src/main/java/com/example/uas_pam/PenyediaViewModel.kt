package com.example.uas_pam


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uas_pam.ui.add.AddViewModel
import com.example.uas_pam.ui.detail.DetailViewModel
import com.example.uas_pam.ui.edit.EditViewModel
import com.example.uas_pam.ui.home.HomeViewModel

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

        initializer {
            HomeViewModel(imtApplication().container.imtRepository)
        }

        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                imtApplication().container.imtRepository
            )
        }
        initializer {
            EditViewModel(
                createSavedStateHandle(),
                imtApplication().container.imtRepository,
                imtApplication().container.userRepository
            )
        }
    }
}