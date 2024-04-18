package com.stu74526.project_74526

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stu74526.project_74526.ui.theme.DorsetColor

@Composable
fun AboutPage(navController: NavController) {
    Column(
        modifier = Modifier
            .background(Color.White),
    ) {
        BackButton(navController = navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Credentials",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(20.dp)
            )

            BottomBarGlobal(home = { navController.navigate(Routes.HomePage.route) },
                historic = { navController.navigate(Routes.OrderPage.route) },
                cart = { navController.navigate(Routes.CartPage.route) },
                profile = { navController.popBackStack() })
        }
    }
}