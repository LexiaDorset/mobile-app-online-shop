package com.stu74526.project_74526

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stu74526.project_74526.ui.theme.DorsetColor

var totalCart = 0.0

@Composable
fun CartMain(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar()
        OrderBody()
        BottomBarGlobal(home = { navController.navigate(Routes.HomePage.route) })
    }
}

@Composable
fun OrderBody() {

    Column(
        modifier = Modifier.fillMaxHeight(0.8f),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //  for (product in productsCart) {
        //    allProducts[product.key]?.let {
        //      totalCart += it.price * product.value
        //}
        //}
        val totalM = remember { mutableDoubleStateOf(totalCart) }
        Column(verticalArrangement = Arrangement.spacedBy(20.dp))
        {

            for (product in productsCart) {

                allProducts[product.key]?.let {
                    CardProductOrder(
                        product = it,
                        quantity = product.value,
                        totalM
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Column {
                Text(
                    text = "Total", fontSize = 23.sp, color = Color.Gray,
                    fontWeight = FontWeight(500)
                )

                Text(
                    text = "${totalM.doubleValue} €", fontSize = 25.sp, color = Color.Black,
                    fontWeight = FontWeight(700)
                )
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(DorsetColor),
                modifier = Modifier.clip(MaterialTheme.shapes.small)
            ) {
                Text(
                    text = "Pay Now", color = Color.White, fontSize = 23.sp,
                    fontWeight = FontWeight(600),
                    modifier = Modifier.padding(
                        start = 22.dp,
                        end = 22.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
                )
            }
        }
    }
}

@Composable
fun CardProductOrder(product: Product, quantity: Int, totalM: MutableDoubleState) {
    var actualQuantity: MutableIntState = remember(product.id) { mutableIntStateOf(quantity) }

    val price = product.price * actualQuantity.intValue
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .height(80.dp)
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.large)
            .fillMaxWidth(0.8f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Button(
            onClick = { },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray),
            contentPadding = PaddingValues(5.dp)
        )
        {
            ShowImageString(
                drawable = product.image,
                modifier = Modifier
                    .fillMaxSize()
            )
        }


        Column {
            Text(text = product.name, fontSize = 18.sp)
            Text(text = "Total: $price", fontSize = 15.sp)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (actualQuantity.intValue > 1) {
                ButtonsQuantity(onClick = {
                    Log.d(
                        "TotalEEE :| totalM BEFORE",
                        totalM.doubleValue.toString()
                    )
                    Log.d(
                        "TotalEEE :BEFORE ",
                        totalCart.toString()
                    )
                    Log.d(
                        "TotalEEE :BEFORE ",
                        product.price.toString()
                    )
                    actualQuantity.intValue -= 1
                    totalCart -= product.price
                    totalM.doubleValue = totalCart
                    Log.d(
                        "TotalEEE :| totalM:",
                        totalM.doubleValue.toString()
                    )
                    Log.d(
                        "TotalEEE : ",
                        totalCart.toString()
                    )
                    Log.d(
                        "TotalEEE : ",
                        price.toString()
                    )
                }, "-")
            } else {
                ButtonsQuantity(onClick = {

                }, "-", enabled = false)
            }
            Text(
                text = actualQuantity.intValue.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            ButtonsQuantity(onClick = {
                Log.d(
                    "TotalEEE :| totalM BEFORE",
                    totalM.doubleValue.toString()
                )
                Log.d(
                    "TotalEEE :BEFORE ",
                    totalCart.toString()
                )
                Log.d(
                    "TotalEEE :BEFORE ",
                    product.price.toString()
                )
                actualQuantity.intValue += 1
                totalCart += product.price
                totalM.doubleValue = totalCart
                Log.d(
                    "TotalEEE :| totalM:",
                    totalM.doubleValue.toString()
                )
                Log.d(
                    "TotalEEE : ",
                    totalCart.toString()
                )
                Log.d(
                    "TotalEEE : ",
                    price.toString()
                )
            }, "+")
        }
    }
}
