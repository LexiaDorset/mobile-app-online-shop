package com.stu74526.project_74526

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProductMain(navController: NavController, productId: String?) {
    if(productId != null) {
        val product = allProducts[productId]
        if (product != null) {
            Column(verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally) {
                ProductImage(product = product, navController = navController)
                ProductBody(product = product)
                Spacer(Modifier.weight(1f))
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        ShowImage(drawable = R.drawable.home,
                            modifier = Modifier.size(50.dp).clickable {
                                navController.popBackStack(Routes.HomePage.route, false)
                            })
                        ShowImage(drawable = R.drawable.historical, modifier = Modifier
                            .size(50.dp)
                            .clickable { /* Handle category click */ })
                        ShowImage(drawable = R.drawable.cart, modifier = Modifier
                            .size(50.dp)
                            .clickable { /* Handle cart click */ })
                        ShowImage(drawable = R.drawable.profile, modifier = Modifier
                            .size(50.dp)
                            .clickable { /* Handle profile click */ })
                    }
                }
            }
        }
        else{
            navController.popBackStack(Routes.HomePage.route, false)
        }
    }
    else{
        navController.popBackStack(Routes.HomePage.route, false)
    }
}

@Composable
fun ProductImage(product: Product, navController: NavController) {
    Box()
    {
        ShowImageString(drawable = product.image, modifier = Modifier
            .fillMaxWidth()
            .height(400.dp))
        BackButton(navController = navController)
    }
}

@Composable
fun BackButton(navController: NavController) {
    Button(
        onClick = {
            navController.popBackStack(Routes.HomePage.route, false)
        },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .padding(0.dp)
            .height(40.dp)
            .width(40.dp),
        contentPadding = PaddingValues(0.dp)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.downarrow),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductBody(product: Product) {
    var actualQuantity by remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    )
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = product.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = product.price.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = product.description, fontSize = 20.sp)
        Row {
            Text(text = "Quantity", fontSize = 21.sp, fontWeight = FontWeight.Bold)
            ButtonsQuantity(onClick = { }, draw = R.drawable.minusunselected, enabled = false)
            Text(text = actualQuantity.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            ButtonsQuantity(onClick = { actualQuantity += 1 }, draw = R.drawable.plusselected)
            Button(onClick = { /*TODO*/ }, modifier = Modifier.border(1.dp, Color.Black, MaterialTheme.shapes.small)) {
                Text(text = "Add to Cart", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ButtonsQuantity(
    onClick: () -> Unit, draw: Int, enabled: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .padding(0.dp)
            .height(30.dp)
            .width(30.dp)
            .padding(paddingValues),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled
    ) {
        ShowImageScale(
            draw = draw,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}