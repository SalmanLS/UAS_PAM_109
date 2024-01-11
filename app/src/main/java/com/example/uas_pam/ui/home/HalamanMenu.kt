package com.example.uas_pam.ui.home

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uas_pam.PenyediaViewModel
import com.example.uas_pam.R
import com.example.uas_pam.navigation.DestinasiNavigasi
import com.example.uas_pam.ui.AllData
import com.example.uas_pam.ui.ImtTopAppBar
import com.example.uas_pam.ui.theme.UAS_PAMTheme

object DestinasiMenu : DestinasiNavigasi {
    override val route = "menu_"
    override val titleRes = "Menu"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMenu(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onViewClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ImtTopAppBar(
                title = "Welcome",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
        BodyHomeMenu(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onAddClick = onAddClick,
            onViewClick = onViewClick
        )
    }
}

@Composable
fun BodyHomeMenu(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onViewClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        ExtendedFloatingActionButton(
            onClick = { onAddClick() },
            icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
            text = { Text(text = "Add Data BMI",fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
            containerColor = Color.LightGray
        )
        Spacer(modifier = Modifier.weight(1f))
        ExtendedFloatingActionButton(
            onClick = { onViewClick() },
            icon = { Icon(Icons.Filled.Menu, "Extended floating action button.") },
            text = { Text(text = "View All Data", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
            containerColor = Color.LightGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HalamanHomeMenuPreview() {
    UAS_PAMTheme {
        HomeScreenMenu(onAddClick = { /*TODO*/ }) {
            
        }
    }
}


