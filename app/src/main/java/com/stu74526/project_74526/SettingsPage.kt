package com.stu74526.project_74526

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
fun SettingsMain(navController: NavController) {
    Column(
        modifier = Modifier
            .background(Color.White),
    ) {
        BackButton(navController = navController)
        SettingsBodyMain(navController = navController)

    }
}

@Composable
fun SettingsBodyMain(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            fontSize = 30.sp,
            color = Color.Black,
            modifier = Modifier.padding(20.dp)
        )
        SettingsBody(white, lightBlack)
        ButtonUpdateSettings(navController = navController)
        BottomBarGlobal(home = { navigateOrPop(navController, Routes.HomePage.route) },
            cart = {
                navigateOrPop(navController, Routes.CartPage.route)
            },
            historic = {
                navigateOrPop(navController, Routes.OrderPage.route)
            },
            profile = { navController.popBackStack() })
    }
}

@Composable
fun ButtonUpdateSettings(navController: NavController) {
    Button(
        onClick = {
            val userAdd: HashMap<String, Any?> = hashMapOf(
                userUsername to usernameSign,
                userFirstName to firstNameSign,
                userLastName to lastNameSign,
                userPhone to phoneSign,
                userCity to citySign,
                userNumber to numberSign,
                userStreet to streetSign,
                userZipcode to zipcodeSign,
                userLat to latSign,
                userLng to lgnSign
            )
            currentUserClass = User(
                username = usernameSign,
                firstName = firstNameSign,
                lastName = lastNameSign,
                email = currentUserClass.email,
                phone = phoneSign,
                number = numberSign,
                street = streetSign,
                city = citySign,
                zipCode = zipcodeSign,
                imageP = currentUserClass.imageP,
                lat = latSign,
                lng = lgnSign
            )
            updateUser(userAdd)
            navController.popBackStack()
        }, colors = ButtonDefaults.buttonColors(DorsetColor),
        modifier = Modifier.clip(MaterialTheme.shapes.small)
    )
    {
        Text(text = "Update Settings", color = Color.White, fontSize = 20.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBody(sColor: Color, tColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(500.dp)
            .padding(bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = usernameSign,
            onValueChange = {
                usernameSign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "Username") },
        )
        OutlinedTextField(
            value = phoneSign,
            onValueChange = {
                phoneSign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "Phone") },
        )
        OutlinedTextField(
            value = numberSign,
            onValueChange = {
                numberSign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "Number") },
        )
        OutlinedTextField(
            value = streetSign,
            onValueChange = {
                streetSign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "Street") },
        )
        OutlinedTextField(
            value = citySign,
            onValueChange = {
                citySign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "City") },
        )
        OutlinedTextField(
            value = zipcodeSign,
            onValueChange = {
                zipcodeSign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "Zipcode") },
        )
        OutlinedTextField(
            value = latSign,
            onValueChange = {
                latSign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "Latitude") },
        )

        OutlinedTextField(
            value = lgnSign,
            onValueChange = {
                lgnSign = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors
                (
                focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor
            ),
            placeholder = { Text(text = "Longitude") },
        )
    }
}