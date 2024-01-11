package com.example.uas_pam.ui.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.R
import com.example.uas_pam.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.ImtTopAppBar
import com.example.uas_pam.ui.add.AddBody
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object EditDestination : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes ="Edit Data"
    const val imtId = "itemId"
    val routeWithArgs = "$route/{$imtId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory),
    pilihanJk: List<String>

) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ImtTopAppBar(
                title =EditDestination.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
        AddBody(
            userUIState = viewModel.userUiState,
            onUserValueChange = viewModel::updateUiStateUser,
            imtUIState = viewModel.imtUiState,
            onImtValueChange = viewModel::updateUiStateImt,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateUser()
                    delay(500)
                    viewModel.updateImt()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            jk = pilihanJk
        )
    }
}