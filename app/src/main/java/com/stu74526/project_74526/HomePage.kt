package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.tasks.await

var actualCategory by mutableStateOf("All")

var categories by mutableStateOf(emptyMap<String, String>())
var products by mutableStateOf(emptyMap<String, Product>())
var Ok = false;

@Composable
fun DisplayImageFromUrl(url: String) {
    val painter = rememberImagePainter(data = url)

    Image(
        painter = painter,
        contentDescription = "Image from URL",
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun HomePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            AppBar()
            SearchBar()
            Category(navController = navController)
        }
        BottomAppBar {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ShowImage(drawable = R.drawable.home, modifier = Modifier.size(50.dp))
                ShowImage(drawable = R.drawable.historical, modifier = Modifier.size(50.dp)
                    .clickable { /* Handle category click */ })
                ShowImage(drawable = R.drawable.cart, modifier = Modifier.size(50.dp)
                    .clickable { /* Handle cart click */ })
                ShowImage(drawable = R.drawable.profile, modifier = Modifier.size(50.dp)
                    .clickable { /* Handle profile click */ })
            }
        }
    }
}

@Composable
fun AppBar()
{
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(
            text = "Online Shopping App",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        ShowImage(drawable = R.drawable.cart, modifier = Modifier.size(30.dp))
        DisplayImageFromUrl("https://thispersondoesnotexist.com/")
    }
}

@Composable
fun SearchBar()
{
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Box(
        Modifier
            .border(1.dp, Color.DarkGray, CircleShape)
            .fillMaxWidth(0.8f),
        contentAlignment = Alignment.CenterStart)
    {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {

            BasicTextField(value = text, onValueChange = {
                if (it.text.length <= 35) {
                    text = it
                } },
                modifier = Modifier
                    .height(30.dp)
                    .padding(5.dp)
                    .weight(1f),
            )
            ShowImage(drawable =R.drawable.search, modifier = Modifier
                .size(30.dp)
                .padding(5.dp, 5.dp, 10.dp, 5.dp))
        }
    }
}

@Composable
fun Category(navController: NavController) {

    LaunchedEffect(Unit) {
        categories = getCategories()
    }

    Row(modifier = Modifier
        .horizontalScroll(rememberScrollState())
        .fillMaxWidth()) {
        Button(onClick = {
            actualCategory = "All"
            products = allProducts
        }){
            Text(text = "All")
        }
        categories.forEach { (key, value) ->
            Button(onClick = {
                actualCategory = key
                products = allProducts.filter { it.value.category_id == key}
            }){
                Text(text = value)
            }
        }
    }
    if(categories.isNotEmpty())
    {
        Product(navController = navController)
    }
}

suspend fun getCategories(): Map<String, String> {
    val categories = mutableMapOf<String, String>()

    categoryCollection.get().addOnSuccessListener { documents ->
        for (document in documents) {
            categories[document.id] = document.data[categoryName].toString()
        }
    }.await()

    return categories
}

@Composable
fun Product(navController: NavController)
{
    LaunchedEffect(Unit) {
        products = getProducts()
    }

    if(products.isNotEmpty())
    {
        Log.d(ContentValues.TAG, "Product: $products")
        val productValue = products.values.toList()
        LazyVerticalGrid(modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.9f),
            columns = GridCells.Adaptive(minSize = 128.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(products.size) { index ->
                val product = productValue[index]
                CardProduct(product,   products.keys.toList()[index], navController)
            }
        }
    }
}

suspend fun getProducts(): Map<String, Product> {
    val products = mutableMapOf<String, Product>()

    productCollection.get().addOnSuccessListener { documents ->
        for (document in documents) {
            val ddata = document.data
            products[document.id] = Product(
                getProductName(ddata),
                getProductImage(ddata),
                10.0,"COUCOU CMOI LA DESCRIPTION DE L'ITEM :)", true, getProductCategoryId(ddata)
            )
        }
        Ok = true

    }.await()
    allProducts = products
    return products
}

@Composable
fun CardProduct(product : Product, productId : String, navController: NavController)
{
    Column(modifier = Modifier
        .clip(MaterialTheme.shapes.large)
        .background(Color.White)
        .width(150.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Button(
            onClick = { navController.navigate(Routes.ProductPage.route + "/${productId}") },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .padding(0.dp)
                .width(150.dp)
                .height(90.dp),
            contentPadding = PaddingValues(5.dp)
        )
        {
            ShowImage(drawable = LocalContext.current.resources.getIdentifier(product.image,
                "drawable", "com.stu74526.project_74526"), modifier = Modifier.fillMaxSize())
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {

            Column(modifier = Modifier
                .clickable { }
                .padding(start = 5.dp, bottom = 5.dp)) {
                Text(text = product.name, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = product.price.toString() + " $", fontSize = 11.sp)
            }
            ShowImage(drawable = R.drawable.heart, modifier = Modifier
                .size(25.dp)
                .padding(end = 5.dp, bottom = 5.dp))
        }
    }
}