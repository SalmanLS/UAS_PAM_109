package com.example.uas_pam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uas_pam.model.JenisKelamin.jk
import com.example.uas_pam.ui.add.AddScreen
import com.example.uas_pam.ui.add.DestinasiAdd
import com.example.uas_pam.ui.detail.DetailDestination
import com.example.uas_pam.ui.detail.DetailScreen
import com.example.uas_pam.ui.edit.EditDestination
import com.example.uas_pam.ui.edit.EditScreen
import com.example.uas_pam.ui.home.DestinasiFirst
import com.example.uas_pam.ui.home.DestinasiHome
import com.example.uas_pam.ui.home.DestinasiMenu
import com.example.uas_pam.ui.home.HalamanFirst
import com.example.uas_pam.ui.home.HomeScreen
import com.example.uas_pam.ui.home.HomeScreenMenu

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
            HalamanFirst(onNextButtonClicked = {navController.navigate(DestinasiMenu.route)})
        }
        composable(DestinasiMenu.route){
            HomeScreenMenu(
                onAddClick = { navController.navigate(DestinasiAdd.route) },
                onViewClick = { navController.navigate(DestinasiHome.route)}
            )
        }
        composable(
            DestinasiHome.route
        ){
            HomeScreen(
                navigateBack = { navController.navigate(DestinasiMenu.route) },
                onDetailClick = { itemId ->
                    navController.navigate("${DetailDestination.route}/$itemId")
                    println("itemId: $itemId")
                })

        }
        composable(
            DestinasiAdd.route
        ){
            AddScreen(
                navigateBack = {navController.navigate(DestinasiHome.route)},
                navigateBackMenu = {navController.navigate((DestinasiMenu.route))},
                pilihanJk = jk
            )
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
                    navigateBack = { navController.navigate(DestinasiHome.route) },
                    navigateToEditItem = {
                        navController.navigate("${EditDestination.route}/$kontakId")
                        println("kontakId: $kontakId")
                    },
                    navigateToMenu = {navController.navigate(DestinasiMenu.route)}
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
                    onNavigateUp = { navController.navigateUp() },
                    pilihanJk = jk
                )
            }
        }
    }
}