package com.stu74526.project_74526

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HomePage.route,
    ) {
        composable(route = Routes.LoginPage.route)
        {
            gLogin(navController = navController)
        }
        composable(route = Routes.SignUpPage.route)
        {
            SignUp(navController = navController)
        }
        composable(route = Routes.HomePage.route)
        {
            HomePage(navController = navController)
        }
        composable(route = Routes.ProductPage.route + "/{productId}",
            arguments = listOf(navArgument("productId") { defaultValue = "0"}))
        {
                backStackEntry ->
            ProductMain(
                navController = navController,
                backStackEntry.arguments?.getString("productId"))
        }
    }
}