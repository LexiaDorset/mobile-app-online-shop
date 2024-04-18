package com.stu74526.project_74526

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun ProfileMain(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight(0.85f)
        )
        {
            DisplayImageFromUrl(
                "https://thispersondoesnotexist.com/", modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = currentUserClass.username,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${currentUserClass.firstName} ${currentUserClass.lastName}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = currentUserClass.email,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }

            ProfileBody(navController)
        }
        BottomBarGlobal(home = { navController.navigate(Routes.HomePage.route) },
            historic = { navController.navigate(Routes.OrderPage.route) },
            cart = { navController.navigate(Routes.CartPage.route) })
    }
}

@Composable
fun ProfileBody(navController: NavController) {
    val context = LocalContext.current
    val activity = context as MainActivity
    Column()
    {
        CustomBox("Cart", onclick = { navController.navigate(Routes.CartPage.route) })

        CustomBox("Historic", onclick = { navController.navigate(Routes.OrderPage.route) })

        CustomBox("Settings", onclick = { navController.navigate(Routes.SettingsPage.route) })

        CustomBox("Log out", onclick = {
            // LOg out de firebase
            activity.signOut(navController)
        })

        CustomBox("About this app")
    }
}

@Composable
fun CustomBox(textString: String, onclick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .clickable { onclick() }
            .clip(MaterialTheme.shapes.medium)
            .border(1.dp, Color.LightGray, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth(0.75f)
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        Text(
            text = textString,
            fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}