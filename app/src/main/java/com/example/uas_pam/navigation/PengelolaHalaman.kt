package com.example.uas_pam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.ui.add.AddUserScreen
import com.example.uas_pam.ui.add.DestinasiAdd
import com.example.uas_pam.ui.detail.DetailDestination
import com.example.uas_pam.ui.detail.DetailScreen
import com.example.uas_pam.ui.edit.EditDestination
import com.example.uas_pam.ui.edit.EditScreen
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
                onDetailClick = { itemId ->
                    navController.navigate("${DetailDestination.route}/$itemId")
                    println("itemId: $itemId")
                })

        }
        composable(
            DestinasiAdd.route
        ){
            AddUserScreen(navigateBack = {navController.popBackStack()})
        }

        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.imtId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val kontakId = backStackEntry.arguments?.getString(DetailDestination.imtId)
            kontakId?.let {
                DetailScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = {
                        navController.navigate("${EditDestination.route}/$kontakId")
                        println("kontakId: $kontakId")
                    }
                )
            }
        }
        composable(
            route = EditDestination.routeWithArgs,
            arguments = listOf(navArgument(EditDestination.imtId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val imtId = backStackEntry.arguments?.getString(EditDestination.imtId)
            imtId?.let {
                EditScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}