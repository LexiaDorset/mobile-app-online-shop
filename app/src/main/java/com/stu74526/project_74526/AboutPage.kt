package com.stu74526.project_74526

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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
            AboutMain()

            BottomBarGlobal(home = { navController.navigate(Routes.HomePage.route) },
                historic = { navController.navigate(Routes.OrderPage.route) },
                cart = { navController.navigate(Routes.CartPage.route) },
                profile = { navController.popBackStack() })
        }
    }
}

@Composable
fun AboutMain() {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
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
        Credits()
    }
}

@Composable
fun Credits() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SimpleTexts()
        TextLinkProfile()
        TextLinkDocs()
        TextMe()
    }
}

@Composable
fun SimpleTexts() {
    Text(
        text = "This app uses Firebase for authentication and data storage.",
        fontSize = 15.sp,
        color = Color.Black,
        modifier = Modifier.padding(20.dp)
    )
    Text(
        text = "Product pictures come from google images.",
        fontSize = 15.sp,
        color = Color.Black,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun TextLinkProfile() {
    val uriHandler = LocalUriHandler.current
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "The profile images are from:",
            fontSize = 15.sp,
            color = Color.Black
        )
        Text(
            text = "https://thispersondoesnotexist.com/",
            fontSize = 15.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { uriHandler.openUri("https://thispersondoesnotexist.com/") }
        )
    }
}

@Composable
fun TextLinkDocs() {
    val uriHandler = LocalUriHandler.current
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "Documentations used:",
            fontSize = 15.sp,
            color = Color.Black
        )
        Text(
            text = "https://firebase.google.com/docs?hl=en",
            fontSize = 15.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { uriHandler.openUri("https://firebase.google.com/docs?hl=en") }
        )
        Text(
            text = "https://developer.android.com/",
            fontSize = 15.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { uriHandler.openUri("https://developer.android.com/") }
        )
    }
}

@Composable
fun TextMe() {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "The app was developed by Lucile PELOU.",
            fontSize = 15.sp,
            color = Color.Black,
        )
        Text(
            text = "Student ID: 74526",
            fontSize = 15.sp,
            color = Color.Black,
        )
    }
}