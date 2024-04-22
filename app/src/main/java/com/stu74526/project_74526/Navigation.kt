package com.stu74526.project_74526

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
        composable(
            route = Routes.ProductPage.route + "/{productId}",
            arguments = listOf(navArgument("productId") { defaultValue = "0" })
        )
        { backStackEntry ->
            ProductMain(
                navController = navController,
                backStackEntry.arguments?.getString("productId")
            )
        }
        composable(route = Routes.CartPage.route)
        {
            CartMain(navController = navController)
        }
        composable(route = Routes.OrderPage.route)
        {
            MainOrderPage(navController = navController)
        }
        composable(
            route = Routes.OrderProductPage.route + "/{orderId}",
            arguments = listOf(navArgument("orderId") { defaultValue = "0" })
        )
        { backStackEntry ->
            OrderProductMain(
                navController = navController,
                backStackEntry.arguments?.getString("orderId")
            )
        }
        composable(route = Routes.ProfilePage.route)
        {
            ProfileMain(navController)
        }
        composable(route = Routes.SettingsPage.route)
        {
            SettingsMain(navController)
        }
        composable(route = Routes.AboutPage.route)
        {
            AboutPage(navController)
        }
    }
}