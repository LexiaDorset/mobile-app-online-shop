package com.stu74526.project_74526

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.stu74526.project_74526.ui.theme.Project_74526Theme

val lightGray = Color.LightGray
val white = Color.White
val black = Color.Black
val lightBlack = Color.DarkGray

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun setEmailP(newEmail: String) {
        email = newEmail
    }

    fun setPasswordP(newPassword: String) {
        password = newPassword
    }

    @Composable
    fun gLogin(navController: NavController)
    {
        MainLogin(lightGray, white, lightBlack, black, navController)
    }

    @Composable
    fun MainLogin(mainColor : Color,
                  secondColor: Color,
                  thirdColor: Color,
                  fourColor: Color, navController: NavController) // Main function for login page
    {
        Column (modifier = Modifier
            .background(mainColor)
            .fillMaxHeight()
            .fillMaxWidth()
            ,horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            // Logo with image of lock screen
            ShowImage(R.drawable.imagelogin,Modifier.padding(bottom=30.dp))

            // First Text for welcome the user
            MainText("Welcome back you've been missed!",thirdColor)

            // Part with TextField, Login Button and icons
            SecondPart(secondColor, thirdColor, fourColor, navController = navController)

            // End with Text
            FinalPart("Not a member?",
                " Register now",thirdColor, navController = navController,
                page = Routes.SignUpPage.route, modifier = Modifier.padding(top = 35.dp))
        }
    }

    @Composable
    fun FinalPart(firstString: String, secondString: String,
                  tColor: Color, modifier: Modifier = Modifier,
                  navController: NavController,
                  page : String) // Text ask if you're not a member, register now
    {
        Row(modifier = modifier)
        {

            CustomText(textString = firstString, color = tColor,
                fontSize = 13.sp,fontWeight = FontWeight.Normal)
            CustomText(textString = secondString, color = tColor,
                fontSize = 13.sp,fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 18.dp)
                    .clickable {
                        navController.navigate(page)
                    })
        }
    }

    @Composable
    fun MainText(textString: String,tColor: Color) // Main text for welcome the user
    {
        Column (modifier = Modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth()
            ,horizontalAlignment = Alignment.CenterHorizontally)
        {
            CustomText(textString,
                color= tColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium)
        }
    }

    @Composable
    fun SecondPart(sColor:Color, tColor: Color, fColor: Color, navController: NavController) // Text fields, Login Button and google and apple icons
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            BoxesPart(sColor, tColor, fColor, navController = navController)
        }
    }




    @Composable
    fun BoxesPart(sColor: Color, tColor: Color, fColor: Color, navController: NavController) // Text fields and Login Button
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            , horizontalAlignment = Alignment.CenterHorizontally) {
            TextFieldPart(sColor, tColor)
            TextPassword(tColor)
            BigButton("Login",sColor, fColor, navController = navController)
        }
    }

    @Composable
    fun TextPassword(tColor: Color) // Text for forgot password
    {
        Row(modifier = Modifier.fillMaxWidth())
        {
            Spacer(modifier = Modifier.weight(1f))
            CustomText(
                "Forgot Password?",
                textAlign = TextAlign.Right,
                modifier = Modifier.padding(bottom = 18.dp),
                fontWeight = FontWeight.Bold,
                color = tColor
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TextFieldPart(sColor: Color, tColor: Color) // Text fields for email and password
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            , horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {

            OutlinedTextField(
                value = email,
                onValueChange = { setEmailP(it)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors
                    ( focusedBorderColor = tColor,
                    unfocusedBorderColor = tColor,
                    focusedPlaceholderColor = tColor,
                    unfocusedPlaceholderColor = tColor,
                    containerColor = sColor),
                placeholder = { Text(text = "Email") },
            )
            OutlinedTextField(
                value = password,
                onValueChange = { setPasswordP(it)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors
                    ( focusedBorderColor = tColor,
                    unfocusedBorderColor = tColor,
                    focusedPlaceholderColor = tColor,
                    unfocusedPlaceholderColor = tColor,
                    containerColor = sColor),
                placeholder = { Text(text = "Password") },
            )

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RoundedTextField(textString: String, modifier: Modifier,
                         sColor: Color, tColor: Color,
                         ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            onValueChange = {
            },
            modifier = modifier,
            colors = TextFieldDefaults.outlinedTextFieldColors
                ( focusedBorderColor = tColor,
                unfocusedBorderColor = tColor,
                focusedPlaceholderColor = tColor,
                unfocusedPlaceholderColor = tColor,
                containerColor = sColor),
            placeholder = { Text(text = textString) },
        )
    }

    @Composable
    fun BigButton(textString: String, sColor: Color, fColor: Color, navController: NavController) // Login or Sign Up Button
    {
        val context = LocalContext.current
        val activity = context as MainActivity
        Button(
            onClick =
            {
                activity.signIn(email, password, navController = navController)
            }, Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(sColor),
        )
        {
            CustomText(textString, color= fColor,
                fontSize = 20.sp,fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all=10.dp))
        }
    }

    @Composable
    fun TextWithBoxes(textString: String, sColor: Color) // Text between two boxes
    {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Box(modifier = Modifier
                .weight(1f)
                .width(100.dp)
                .height(1.dp)
                .background(sColor))

            CustomText(textString = textString, color = sColor,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 14.sp)

            Box(modifier = Modifier
                .weight(1f)
                .width(100.dp)
                .height(1.dp)
                .background(sColor))
        }
    }

    @Composable
    fun CustomText(textString: String,
                   color: Color = Color.Black,
                   fontSize: TextUnit = 12.sp,
                   fontWeight: FontWeight = FontWeight.Normal,
                   textAlign : TextAlign = TextAlign.Center,
                   @SuppressLint("ModifierParameter")
                   modifier: Modifier = Modifier) // Custom Text with default parameters
    {
        Text(
            text = textString,
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign)
    }