package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OrderProductMain(navController: NavController, string: String?) {
    if (string != null) {
        val order = orders[string]
        if (order != null) {
            Column()
            {
                BackButton(navController = navController)
                Text(
                    text = "Order Information",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OrderProductBody(order = order, navController = navController)
                    OrderProductBottom(order)
                    BottomBarGlobal(
                        home = { navController.navigate(Routes.HomePage.route) },
                        historic = { navController.popBackStack() },
                        cart = { navController.navigate(Routes.CartPage.route) },
                        profile = { navController.navigate(Routes.ProfilePage.route) }
                    )
                }
            }
        } else {
            navController.popBackStack(Routes.OrderPage.route, false)
        }
    } else {
        navController.popBackStack(Routes.OrderPage.route, false)
    }
}

@Composable
fun OrderProductBottom(order: Order) {
    val euros = order.total.toInt()
    val cents = ((order.total - euros) * 100).toInt()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${order.products.size} items",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Row()
            {
                Text(
                    text = "Total: $euros",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "€${cents.toString().padStart(2, '0')}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        val date = order.date.toDate()

        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        Text(text = formattedDate, fontWeight = FontWeight(550), fontSize = 20.sp)
    }

}

@Composable
fun OrderProductBody(order: Order, navController: NavController) {
    var prod = order.products

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxHeight(0.75f)
    ) {
        prod.forEach()
        {
            item {
                var actualProduct = allProducts[it.key]
                if (actualProduct != null) {

                    val price = ((actualProduct.price) * it.value)
                    val euros = price.toInt()
                    val cents = ((price - euros) * 100).toInt()

                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .height(80.dp)
                            .border(1.dp, Color.LightGray, MaterialTheme.shapes.large)
                            .fillMaxWidth(0.9f)
                            .padding(end = 15.dp)
                            .clickable { navController.navigate(Routes.ProductPage.route + "/${it.key}") },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.LightGray)
                                .padding(5.dp)
                                .clip(MaterialTheme.shapes.small),
                        )
                        {
                            ShowImageString(
                                drawable = actualProduct.image,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        Row(modifier = Modifier.padding(start = 10.dp))
                        {
                            Column {
                                Text(
                                    text = actualProduct.name, fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Row()
                                {
                                    Text(text = "Total: $euros", fontSize = 15.sp)
                                    Text(
                                        text = "€${cents.toString().padStart(2, '0')}",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "x" + it.value.toString(),
                                        fontSize = 19.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight(600),
                                        modifier = Modifier.padding(
                                            end = 5.dp,
                                            top = 10.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}