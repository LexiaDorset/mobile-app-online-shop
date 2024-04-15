package com.stu74526.project_74526

sealed class Routes(val route: String) {
    object LoginPage : Routes("login_page")
    object SignUpPage : Routes("sign_up_page")
    object HomePage : Routes("home_page")
    object ProductPage : Routes("product_page/{productId}")

    object CartPage : Routes("cart_page")
}