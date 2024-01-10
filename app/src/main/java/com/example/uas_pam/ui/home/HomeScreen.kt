package com.example.uas_pam.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.R
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
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            Row {
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
//                FloatingActionButton(
//                    onClick = { viewModel.triggerRefresh() },
//                    shape = MaterialTheme.shapes.medium,
//                    modifier = Modifier.padding(18.dp)
//                    ) {
//                    Icon(
//                        imageVector = Icons.Default.Refresh,
//                        contentDescription = ""
//                    )
//                }
            }
        },
    ) { innerPadding ->
        val uiStateData by viewModel.homeUIState.collectAsState()
        BodyHome(
            itemAll = uiStateData.alldata,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDataClick = onDetailClick
        )
    }
}

@Composable
fun BodyHome(
    itemAll: List<AllData>,
    modifier: Modifier = Modifier,
    onDataClick: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (itemAll.isEmpty()) {
            Text(
                text = "Tidak ada data IMT",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ListAll(
                itemAll = itemAll,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                onItemDataClick = { onDataClick(it.idData) }
            )
        }
    }
}

@Composable
fun ListAll(
    itemAll: List<AllData>,
    modifier: Modifier = Modifier,
    onItemDataClick: (AllData) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        this.items(itemAll, key = { it.idData }) { all ->
            ListAll(
                allData = all,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onItemDataClick(all) }
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}



@Composable
fun ListAll(
    allData: AllData,
    modifier: Modifier = Modifier
) {
    val image = painterResource(id = R.drawable.baseline_person_24)
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Column (
                ){
                    Text(text = allData.namaUser, style = MaterialTheme.typography.titleLarge)
                    Text(text = allData.umurUser + " Tahun", style = MaterialTheme.typography.titleMedium)
                    Text(text = allData.jeniskUser, style = MaterialTheme.typography.titleMedium)
                }

                Spacer(Modifier.weight(2f))
                Image(
                    painter = image,
                    contentDescription = "",
                    modifier.size(120.dp)
                )

            }
            Card {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(text = "TB: " + allData.tbUser.toString() + " cm", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.weight(1f))
                    Text(text = "BB: " + allData.bbUser.toString() + " kg", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.weight(1f))
                    Text(text = "IMT: " + allData.imtClass, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}