package com.example.uas_pam.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.R
import com.example.uas_pam.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.AllData
import com.example.uas_pam.ui.ImtTopAppBar
import com.example.uas_pam.ui.theme.UAS_PAMTheme

object DestinasiHome : DestinasiNavigasi {
    override val route = "home_"
    override val titleRes = "Home"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ImtTopAppBar(
                title = "BMI Data",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
    ) { innerPadding ->
        val uiStateData by viewModel.homeUIState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
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
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        if (itemAll.isEmpty()) {
            Card(
                colors = CardDefaults.cardColors(Color.LightGray)
            ) {
                Text(
                    text = "The Data is Empty",
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(32.dp)
                )
            }
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
        modifier = modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = image, contentDescription = "")
                Card(
                    colors = CardDefaults.cardColors(Color.LightGray)
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = allData.namaUser,
                            style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily.Serif),
                        )
                        Text(
                            text = allData.umurUser + " Tahun",
                            style = TextStyle(fontFamily = FontFamily.Serif),
                        )
                        Text(
                            text = allData.jeniskUser,
                            style = TextStyle(fontFamily = FontFamily.Serif),
                        )
                    }
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "TB: " + allData.tbUser.toString() + " cm",
                    style = TextStyle(fontFamily = FontFamily.Serif),
                )
                Text(
                    text = "BB: " + allData.bbUser.toString() + " kg",
                    style = TextStyle(fontFamily = FontFamily.Serif),
                )
                Text(
                    text = "IMT: " + allData.imtClass,
                    style = TextStyle(fontFamily = FontFamily.Serif),
                )
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HalamanHomePreview() {
//    UAS_PAMTheme {
//        HomeScreen(navigateToItemEntry = {})
//    }
//}