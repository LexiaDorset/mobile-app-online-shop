package com.stu74526.project_74526

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
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MainOrderPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar()
        BodyOrder(navController)
        BottomBarGlobal(home = { navController.navigate(Routes.HomePage.route) },
            historic = { navController.navigate(Routes.OrderPage.route) },
            cart = {
                navController.navigate(Routes.CartPage.route)
            },
            profile = { navController.navigate(Routes.ProfilePage.route) })
    }
}

@Composable
fun BodyOrder(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.9f)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Orders",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            orders.forEach()
            { order ->
                item {
                    OrderCard(order.value, order.key, navController = navController)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: Order, orderId: String, navController: NavController) {
    var prod = order.products

    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .height(80.dp)
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.large)
            .fillMaxWidth(0.9f)
            .padding(end = 15.dp)
            .clickable { navController.navigate("${Routes.OrderProductPage.route}/${orderId}") },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .size(80.dp)
                .padding(5.dp),
        )
        {
            ShowImageString(
                drawable = allProducts[prod.firstNotNullOf { it.key }]?.image ?: "",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        val euros = order.total.toInt()
        val cents = ((order.total - euros) * 100).toInt()
        Column(modifier = Modifier.padding(10.dp))
        {
            Text(
                text = "${prod.size} items",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Row()
            {
                Text(
                    text = "$euros",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "€${cents.toString().padStart(2, '0')}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        val date = order.date.toDate()

        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = formattedDate)
        }
    }
}
