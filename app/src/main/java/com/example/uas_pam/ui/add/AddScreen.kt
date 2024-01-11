package com.example.uas_pam.ui.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.R
import com.example.uas_pam.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.DetailImt
import com.example.uas_pam.ui.DetailUser
import com.example.uas_pam.ui.ImtTopAppBar
import com.example.uas_pam.ui.ImtUIState
import com.example.uas_pam.ui.UserUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiAdd : DestinasiNavigasi {
    override val route = "add_"
    override val titleRes = "Add Data"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    addViewModel: AddViewModel = viewModel(factory = PenyediaViewModel.Factory),
    pilihanJk: List<String>

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
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
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
                .fillMaxWidth(),
            jk = pilihanJk
        )
    }
}

@Composable
fun AddBody(
    userUIState: UserUIState,
    imtUIState: ImtUIState,
    onUserValueChange: (DetailUser) -> Unit,
    onImtValueChange: (DetailImt) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    jk: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputUser(
            detailUser = userUIState.detailUser,
            onValueChange = onUserValueChange,
            modifier = Modifier.fillMaxWidth(),
            pilihanJk = jk
        )
        FormInputImt(
            detailImt = imtUIState.detailImt,
            onValueChange = onImtValueChange,
            modifier = Modifier.fillMaxWidth(),

            )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.LightGray)
        ) {
            Text("Submit", fontFamily = FontFamily.Serif, color = Color.Black)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputUser(
    pilihanJk: List<String>,
    detailUser: DetailUser,
    modifier: Modifier = Modifier,
    onValueChange: (DetailUser) -> Unit = {},
    enabled: Boolean = true
) {
    var  jenisk by rememberSaveable { mutableStateOf("") }
    Text(text = "Person Data:", fontFamily = FontFamily.Serif)
    Card (
        colors = CardDefaults.cardColors(Color.LightGray)
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            OutlinedTextField(
                value = detailUser.nama,
                onValueChange = { onValueChange(detailUser.copy(nama = it)) },
                label = { Text("Nama", fontFamily = FontFamily.Serif) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true,
            )
            OutlinedTextField(
                value = detailUser.umur,
                onValueChange = { onValueChange(detailUser.copy(umur = it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Umur", fontFamily = FontFamily.Serif) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
            Text(text = "Jenis Kelamin :", fontFamily = FontFamily.Serif)
            pilihanJk.forEach { item ->
                Row(
                    modifier = Modifier.selectable(
                        selected = jenisk == item,
                        onClick = {
                            jenisk = item
                            onValueChange(detailUser.copy(jenisk = item))
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = jenisk == item,
                        onClick = {
                            jenisk = item
                            onValueChange(detailUser.copy(jenisk = item))
                        }
                    )
                    Text(item)
                }

            }
    }



    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputImt(
    detailImt: DetailImt,
    modifier: Modifier = Modifier,
    onValueChange: (DetailImt) -> Unit = {},
) {
    val image = painterResource(id = R.drawable.body)
    Text(text = "BMI Classification Data :", fontFamily = FontFamily.Serif)
    Card (
        colors = CardDefaults.cardColors(Color.LightGray)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            OutlinedTextField(
                value = detailImt.tb.toString(),
                onValueChange = {
                    val newValue = if (it.isNotBlank()) it.toLong() else 0L
                    onValueChange(detailImt.copy(tb = newValue))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Tinggi Badan dalam CM",fontFamily = FontFamily.Serif) },

                singleLine = true
            )
            Image(painter = image, contentDescription = "", modifier = Modifier.size(200.dp))
            OutlinedTextField(
                value = detailImt.bb.toString(),
                onValueChange = {
                    val newValue = if (it.isNotBlank()) it.toLong() else 0L
                    onValueChange(detailImt.copy(bb = newValue))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Berat Badan dalam KG", fontFamily = FontFamily.Serif) },
                singleLine = true,
            )

        }
    }


}