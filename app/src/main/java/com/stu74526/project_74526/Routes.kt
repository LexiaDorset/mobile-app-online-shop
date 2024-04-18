package com.stu74526.project_74526

sealed class Routes(val route: String) {
    object LoginPage : Routes("login_page")
    object SignUpPage : Routes("sign_up_page")
    object HomePage : Routes("product_page")
    object ProductPage : Routes("product_page/{productId}")
    object CartPage : Routes("cart_page")
    object OrderPage : Routes("order_page")
    object OrderProductPage : Routes("order_page/{orderId}")
    object ProfilePage : Routes("profile_page")
    object SettingsPage : Routes("settings_page")
    object AboutPage : Routes("about_page")
}