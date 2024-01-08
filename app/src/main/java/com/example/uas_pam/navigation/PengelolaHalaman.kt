package com.example.uas_pam.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.uas_pam.ui.add.AddUserScreen
//import com.example.uas_pam.ui.add.DestinasiAdd
//
//@Composable
//fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
//    NavHost(
//        navController = navController,
//        startDestination = DestinasiAdd.route,
//        modifier = Modifier
//    ){
//        composable(
//            DestinasiAdd.route
//        ){
//            AddUserScreen(navigateBack = { /*TODO*/ })
//        }
//    }
//}