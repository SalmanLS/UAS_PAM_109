package com.example.uas_pam.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.AllData
import com.example.uas_pam.ui.ImtTopAppBar

object DestinasiHome : DestinasiNavigasi {
    override val route = "home_"
    override val titleRes = "Home"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ImtTopAppBar(
                title = "Imt Application",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        },
    ) { innerPadding ->
        val uiStateData by viewModel.homeUIState.collectAsState()
        BodyHome(
            itemAll = uiStateData.alldata,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onSiswaClick = onDetailClick
        )
    }
}

@Composable
fun BodyHome(
    itemAll: List<AllData>,
    modifier: Modifier = Modifier,
    onSiswaClick: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (itemAll.isEmpty()) {
            Text(
                text = "Tidak ada data Kontak",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListAll(
                itemAll = itemAll,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                onItemClick = {  }
            )
        }
    }
}
@Composable
fun ListAll(
    itemAll: List<AllData>,
    modifier: Modifier = Modifier,
    onItemClick: (AllData) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        this.items(itemAll, key = { it.idData }) { all ->
            DataAll(
                allData = all,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun DataAll(
    allData: AllData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = allData.namaUser,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = allData.jeniskUser,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = allData.umurUser,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = allData.tbUser.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = allData.bbUser.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = allData.imtClass,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}