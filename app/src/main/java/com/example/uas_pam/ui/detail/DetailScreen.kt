package com.example.uas_pam.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.R
import com.example.uas_pam.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.AllData
import com.example.uas_pam.ui.DetailUIState
import com.example.uas_pam.ui.ImtTopAppBar
import com.example.uas_pam.ui.theme.UAS_PAMTheme
import com.example.uas_pam.ui.toAllData
import kotlinx.coroutines.launch

object DetailDestination : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = "Detail Data"
    const val imtId = "itemId"
    val routeWithArgs = "$route/{$imtId}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateToEditItem: (String) -> Unit,
    navigateToMenu: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            ImtTopAppBar(
                title = DetailDestination.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(4.dp),
            ){
                ExtendedFloatingActionButton(
                    onClick = {  deleteConfirmationRequired = true },
                    icon = { Icon(Icons.Filled.Delete, "") },
                    text = { Text(text = "Delete",fontFamily = FontFamily.Serif ) },
                    containerColor = Color.LightGray
                )
                Spacer(modifier = Modifier.width(16.dp))
                ExtendedFloatingActionButton(
                    onClick = { navigateToEditItem(uiState.value.allDataUi.id) },
                    icon = { Icon(Icons.Filled.Edit, "") },
                    text = { Text(text = "Edit", fontFamily = FontFamily.Serif) },
                    containerColor = Color.LightGray
                )
                Spacer(modifier = Modifier.width(16.dp))
                ExtendedFloatingActionButton(
                    onClick = { navigateToMenu() },
                    icon = { Icon(Icons.Filled.Home, "") },
                    text = { Text(text = "Menu", fontFamily = FontFamily.Serif) },
                    containerColor = Color.LightGray
                )
            }
            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        coroutineScope.launch {
                            viewModel.deleteKontak()
                            navigateBack()
                        }
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(12.dp)
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
        ItemDetailsBody(
            detailUIState = uiState.value,

            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),

            )
    }
}
@Composable
private fun ItemDetailsBody(
    detailUIState: DetailUIState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ItemDetails(
            allData  = detailUIState.allDataUi.toAllData(), modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ItemDetails(
    allData: AllData, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ItemDetailsRow(
                labelResID ="Nama",
                itemDetail = allData.namaUser,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID = "Jenis Kelamin",
                itemDetail = allData.jeniskUser,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID ="Umur",
                itemDetail = allData.umurUser,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID ="Tinggi Badan",
                itemDetail = allData.tbUser.toString(),
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID ="Berat Badan",
                itemDetail = allData.bbUser.toString(),
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID ="IMT Class",
                itemDetail = allData.imtClass,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
        }

    }
}
@Composable
private fun ItemDetailsRow(
    labelResID: String, itemDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = labelResID, fontWeight = FontWeight.Bold,fontFamily = FontFamily.Serif,)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontStyle = FontStyle.Italic,fontFamily = FontFamily.Serif,)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Are you sure?",fontFamily = FontFamily.Serif) },
        text = { Text("Delete", fontFamily = FontFamily.Serif) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "No", fontFamily = FontFamily.Serif)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes", fontFamily = FontFamily.Serif)
            }
        },
        containerColor = Color.LightGray
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    UAS_PAMTheme {
//        DetailScreen(navigateToEditItem = {} , navigateBack = { /*TODO*/ })
//    }
//}