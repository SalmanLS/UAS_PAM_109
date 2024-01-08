package com.example.uas_pam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uas_pam.ui.add.AddUserScreen
import com.example.uas_pam.ui.add.DestinasiAdd
import com.example.uas_pam.ui.home.DestinasiFirst
import com.example.uas_pam.ui.home.DestinasiHome
import com.example.uas_pam.ui.home.HalamanFirst
import com.example.uas_pam.ui.home.HomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiFirst.route,
        modifier = Modifier
    ){
        composable(
            DestinasiFirst.route
        ){
            HalamanFirst(onNextButtonClicked = {navController.navigate(DestinasiHome.route)})
        }
        composable(
            DestinasiHome.route
        ){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiAdd.route) },
                modifier = Modifier
                )
        }
        composable(
            DestinasiAdd.route
        ){
            AddUserScreen(navigateBack = {navController.popBackStack()})
        }
    }
}