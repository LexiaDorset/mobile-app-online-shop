package com.stu74526.project_74526

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stu74526.project_74526.ui.theme.DorsetColor


@Composable
fun ProductMain(navController: NavController, productId: String?) {
    if (productId != null) {
        val product = allProducts[productId]
        if (product != null) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                ProductImage(product = product, navController = navController)
                ProductBody(product = product)
                BottomBarGlobal(home = { navController.popBackStack() },
                    cart = {
                        navigateOrPop(navController, Routes.CartPage.route)
                    },
                    historic = {
                        navigateOrPop(navController, Routes.OrderPage.route)
                    },
                    profile = { navigateOrPop(navController, Routes.ProfilePage.route) })
            }
        } else {
            HomePage(navController = navController)
            //navController.popBackStack(Routes.HomePage.route, false)
        }
    } else {
        HomePage(navController = navController)
        // navController.popBackStack(Routes.HomePage.route, false)
    }
}

@Composable
fun ProductImage(product: Product, navController: NavController) {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .clip(MaterialTheme.shapes.medium)
    )
    {
        ShowImageString(
            drawable = product.image, modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
        BackButton(navController = navController)
    }
}

@Composable
fun BackButton(navController: NavController) {
    Button(
        onClick = {
            navController.popBackStack()
        },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .padding(5.dp)
            .height(40.dp)
            .width(40.dp),
        contentPadding = PaddingValues(0.dp)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductBody(product: Product) {
    var isFavorite = remember(product.id) { mutableStateOf(product.favorite) }

    var actualQuantity by remember {
        mutableIntStateOf(productsCart.contains(product.id).let {
            if (it) productsCart[product.id]!! else 1
        })
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.82f)
            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        item {
            ProductDetailsFirst(product = product)
            if (product.description.count() > 350) {
                var expanded by remember { mutableStateOf(false) }
                Text(
                    text = if (expanded) product.description else product.description.take(300) + "...",
                    fontSize = 19.sp,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            } else {
                Text(
                    text = product.description,
                    fontSize = 19.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    if (actualQuantity > 1) {
                        ButtonsQuantity(onClick = { actualQuantity -= 1 }, "-")
                    } else {
                        ButtonsQuantity(onClick = { actualQuantity -= 1 }, "-", enabled = false)
                    }
                    Text(
                        text = actualQuantity.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    ButtonsQuantity(onClick = { actualQuantity += 1 }, "+")
                }
                val heartIcon = if (isFavorite.value) R.drawable.heart2 else R.drawable.heart
                ShowImage(
                    drawable = heartIcon,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 5.dp, bottom = 5.dp)
                        .clickable {
                            isFavorite.value = !isFavorite.value
                            product.favorite = isFavorite.value
                            updateFavorite(product.id)
                            if (actualCategory == "Favorite")
                                products = allProducts.filter { it.value.favorite }
                        }
                )
            }
            ColumnAdd(product = product, actualQuantity = actualQuantity)
        }
    }
}

@Composable
fun ProductDetailsFirst(product: Product) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = product.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            text = product.price.toString() + "€",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ColumnAdd(product: Product, actualQuantity: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonAddCart(product = product, actualQuantity = actualQuantity)
    }
}

@Composable
fun ButtonAddCart(product: Product, actualQuantity: Int) {
    Button(
        onClick = {
            val productCartData: HashMap<String, Any?> = hashMapOf(
                "product_id" to product.id,
                "user_id" to userId,
                "quantity" to actualQuantity,
            )
            if (productsCart.containsKey(product.id)) {
                totalCart -= product.price * productsCart[product.id]!!
                totalCart += product.price * actualQuantity
                updateProductCart(productCartData)
            } else {
                totalCart += product.price * actualQuantity
                addProductCart(productCartData)
            }
            productsCart[product.id] = actualQuantity
            sizeProduct.intValue += 1
        },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clip(MaterialTheme.shapes.small),
        colors = ButtonDefaults.buttonColors(DorsetColor),
    ) {
        Text(text = "Add to Cart", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ButtonsQuantity(
    onClick: () -> Unit, textString: String, enabled: Boolean = true,
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
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(Color.LightGray),
    ) {
        Text(
            text = textString, fontSize = 20.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, color = Color.Black
        )
    }
}