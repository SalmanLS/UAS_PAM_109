package com.example.uas_pam.ui.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.DetailImt
import com.example.uas_pam.ui.DetailUser
import com.example.uas_pam.ui.ImtTopAppBar
import com.example.uas_pam.ui.ImtUIState
import com.example.uas_pam.ui.UserUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiAdd : DestinasiNavigasi{
    override val route = "add_"
    override val titleRes = "Add Data"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    addViewModel: AddViewModel = viewModel(factory = PenyediaViewModel.Factory),

    ) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ImtTopAppBar(
                title = DestinasiAdd.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->

        AddBody(
            userUIState = addViewModel.uiStateUser,
            onUserValueChange = addViewModel::updateUiStateUser,
            imtUIState = addViewModel.uiStateImt,
            onImtValueChange = addViewModel::updateUiStateImt,
            onSaveClick = {
                coroutineScope.launch {
                    addViewModel.saveUser()
                    delay(500)
                    addViewModel.saveImt()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}
@Composable
fun AddBody(
    userUIState: UserUIState,
    imtUIState: ImtUIState,
    onUserValueChange: (DetailUser) -> Unit,
    onImtValueChange : (DetailImt) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputUser(
            detailUser = userUIState.detailUser,
            onValueChange = onUserValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        FormInputImt(
            detailImt = imtUIState.detailImt,
            onValueChange = onImtValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputUser(
    detailUser: DetailUser,
    modifier: Modifier = Modifier,
    onValueChange: (DetailUser) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = detailUser.nama,
            onValueChange = { onValueChange(detailUser.copy(nama = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailUser.jenisk,
            onValueChange = { onValueChange(detailUser.copy(jenisk = it)) },
            label = { Text("Jenis Kelamin") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailUser.umur,
            onValueChange = { onValueChange(detailUser.copy(umur = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Umur") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputImt(
    detailImt: DetailImt,
    modifier: Modifier = Modifier,
    onValueChange: (DetailImt) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = detailImt.bb.toString(),
            onValueChange = {
                val newValue = if (it.isNotBlank()) it.toLong() else 0L
                onValueChange(detailImt.copy(bb = newValue))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Berat Badan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailImt.tb.toString(),
            onValueChange = {
                val newValue = if (it.isNotBlank()) it.toLong() else 0L
                onValueChange(detailImt.copy(tb = newValue))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Tinggi Badan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}