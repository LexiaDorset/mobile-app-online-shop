package com.stu74526.project_74526


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

var emailSign by mutableStateOf("")
var passwordSign by mutableStateOf("")
var usernameSign by mutableStateOf("")
var firstNameSign by mutableStateOf("")
var lastNameSign by mutableStateOf("")
var phoneSign by mutableStateOf("")
var numberSign by mutableStateOf("")
var streetSign by mutableStateOf("")
var citySign by mutableStateOf("")
var zipcodeSign by mutableStateOf("")
var latSign by mutableStateOf("")
var lgnSign by mutableStateOf("")

fun seEmailSign(Email: String) {
    emailSign = Email
}

fun sePasswordSign(Password: String) {
    passwordSign = Password
}

@Composable
fun SignUp(navController: NavController) {
    MainSignUp(lightGray, white, lightBlack, black, navController)
}

@Composable
fun MainSignUp(
    mainColor: Color,
    secondColor: Color,
    thirdColor: Color,
    fourColor: Color, navController: NavController
) // Main function for sign up page
{
    Column(
        modifier = Modifier
            .background(mainColor)
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        // Logo with image of lock screen
        ShowImage(R.drawable.imagelogin, Modifier.padding(bottom = 30.dp))

        // First Text for welcome the user
        MainText("Let's create an account for you!", thirdColor)

        // Part with TextField, Login Button and icons
        BoxesPartSign(secondColor, thirdColor, fourColor, navController = navController)

        // End with Text
        FinalPart(
            "Already a member?", " Login now",
            thirdColor, modifier = Modifier.padding(top = 35.dp),
            onclick = { navController.popBackStack(Routes.LoginPage.route, true) }
        )
    }
}

@Composable
fun BoxesPartSign(
    sColor: Color,
    tColor: Color,
    fColor: Color,
    navController: NavController
) // Text fields and Sign Up Button
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldPartSignUp(sColor, tColor)
        BigButton2("Sign Up", sColor, fColor, navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldPartSignUp(sColor: Color, tColor: Color) // Text fields for email, password and confirm
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = emailSign,
            onValueChange = {
                seEmailSign(it)
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
            placeholder = { Text(text = "Email") },
        )
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
            value = firstNameSign,
            onValueChange = {
                firstNameSign = it
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
            placeholder = { Text(text = "First Name") },
        )
        OutlinedTextField(
            value = lastNameSign,
            onValueChange = {
                lastNameSign = it
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
            placeholder = { Text(text = "Last Name") },
        )
        OutlinedTextField(
            value = passwordSign,
            onValueChange = {
                sePasswordSign(it)
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
            placeholder = { Text(text = "Password") },
        )

    }
}

@Composable
fun BigButton2(
    textString: String,
    sColor: Color,
    fColor: Color,
    navController: NavController
) // Login or Sign Up Button
{
    val context = LocalContext.current
    val activity = context as MainActivity
    Button(
        onClick =
        {
            activity.createAccount(
                emailSign, passwordSign, usernameSign,
                firstNameSign, lastNameSign, phoneSign, numberSign,
                streetSign, citySign, zipcodeSign, latSign, lgnSign,
                navController
            )
        },
        Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(sColor),
    )
    {
        CustomText(
            textString, color = fColor,
            fontSize = 20.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(all = 10.dp)
        )
    }
}
