package com.example.uas_pam.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uas_pam.data.ImtRepository
import com.example.uas_pam.model.Imt
import com.example.uas_pam.ui.AllData
import com.example.uas_pam.ui.HomeUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
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

    val allDataListFlow: Flow<List<AllData>> = imtRepository.getAllWithUser()
        .map { pairsList ->
            pairsList.mapNotNull { (imt, user) ->
                if (user != null) {
                    AllData(
                        idData = imt.idData,
                        namaUser = user.namaUser,
                        jeniskUser = user.jeniskUser,
                        umurUser = user.umurUser,
                        bbUser = imt.bbUser,
                        tbUser = imt.tbUser,
                        imtClass = imt.imtClass
                    )
                } else {
                    null
                }
            }
        }.flowOn(Dispatchers.IO)

    val homeUIState: StateFlow<HomeUIState> = allDataListFlow
        .filterNotNull()
        .map {
            HomeUIState(alldata = it.toList(), it.size)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUIState()
        )

}