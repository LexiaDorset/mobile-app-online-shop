package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.stu74526.project_74526.ui.theme.DorsetColor
import com.stu74526.project_74526.ui.theme.EpitaColor
import com.stu74526.project_74526.ui.theme.Purple40
import com.stu74526.project_74526.ui.theme.RoyalBlue
import com.stu74526.project_74526.ui.theme.White20
import com.stu74526.project_74526.ui.theme.White40
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

var actualCategory by mutableStateOf("All")

var categories by mutableStateOf(emptyMap<String, String>())
var products by mutableStateOf(emptyMap<String, Product>())

/* Composable Functions */

@Composable
fun HomePage(navController: NavController) {
    if (currentUser == null) {
        navController.navigate(Routes.LoginPage.route)
        return
    }
    var isNavigationReady by remember { mutableStateOf(collectionsSet) }

    LaunchedEffect(Unit) {
        productsCart = getProductCart()
        orders = getOrders()
        isNavigationReady = true
        collectionsSet = true
    }
    if (isNavigationReady) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeContent(navController = navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        AppBar()
        //SearchBar()
        Category(navController = navController)
    }
    BottomBarGlobal(cart = { navController.navigate(Routes.CartPage.route) },
        historic = { navController.navigate(Routes.OrderPage.route) },
        profile = { navController.navigate(Routes.ProfilePage.route) })
}

@Composable
fun AppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RoyalBlue)
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        ShowImage(drawable = R.drawable.logo)
        DisplayImageFromUrl(
            "https://thispersondoesnotexist.com/", modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun SearchBar() {
    Box(
        Modifier
            .border(1.dp, Color.DarkGray, CircleShape)
            .fillMaxWidth(0.8f),
        contentAlignment = Alignment.CenterStart
    )
    {
        SearchBarContent()
    }
}

@Composable
fun SearchBarContent() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {

        BasicTextField(
            value = text,
            onValueChange = {
                if (it.text.length <= 35) {
                    text = it
                }
            },
            modifier = Modifier
                .height(30.dp)
                .padding(5.dp)
                .weight(1f)
        )
        ShowImage(
            drawable = R.drawable.search, modifier = Modifier
                .size(30.dp)
                .padding(5.dp, 5.dp, 10.dp, 5.dp)
        )
    }
}

@Composable
fun Category(navController: NavController) {
    LaunchedEffect(Unit) {
        categories = getCategories()
    }
    LazyRow() {
        item {
            ButtonCategory("All", onclick = {
                actualCategory = "All"
                products = allProducts
            }, productId = "All")
        }
        item {
            ButtonCategory(textString = "Favorite", onclick = {
                actualCategory = "Favorite"
                products = allProducts.filter { it.value.favorite }
            }, productId = "Favorite")
        }
        categories.forEach { (key, value) ->
            item {
                ButtonCategory(textString = value, onclick = {
                    actualCategory = key
                    products = allProducts.filter { it.value.category_id == key }
                }, productId = key)
            }
        }
    }
    if (categories.isNotEmpty()) {
        Product(navController = navController)
    }
}

@Composable
fun ButtonCategory(textString: String, onclick: () -> Unit, productId: String) {
    if (actualCategory == productId) {
        Button(
            onClick = { onclick() },
            colors = ButtonDefaults.buttonColors(DorsetColor)
        ) {
            Text(text = textString, color = Color.White)
        }
    } else {
        Button(
            onClick = { onclick() },
            colors = ButtonDefaults.buttonColors(EpitaColor)
        ) {
            Text(text = textString, color = Color.Black)
        }
    }
}


@Composable
fun Product(navController: NavController) {
    if (products.isNotEmpty()) {
        Log.d(ContentValues.TAG, "Product: $products")
        val productValue = products.values.toList()
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.9f),
            columns = GridCells.Adaptive(minSize = 128.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(products.size) { index ->
                val product = productValue[index]
                CardProduct(product, products.keys.toList()[index], navController)
            }
        }
    }
}

@Composable
fun CardProduct(product: Product, productId: String, navController: NavController) {

    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.large)
            .width(160.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        CardProductContent(
            product = product, productId = productId,
            navController = navController
        )
    }
}

@Composable
fun CardProductContent(product: Product, productId: String, navController: NavController) {

    ButtonImageCard(product, productId, navController)
    CardProductRow(product, productId, navController)
}

@Composable
fun ButtonImageCard(product: Product, productId: String, navController: NavController) {
    Button(
        onClick = { navController.navigate(Routes.ProductPage.route + "/${productId}") },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .padding(0.dp)
            .width(160.dp)
            .height(90.dp)
            .background(Color.LightGray),
        contentPadding = PaddingValues(5.dp)
    )
    {
        ShowImage(
            drawable = LocalContext.current.resources.getIdentifier(
                product.image,
                "drawable", "com.stu74526.project_74526"
            ), modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CardProductRow(product: Product, productId: String, navController: NavController) {
    Row(
        modifier = Modifier
            .width(160.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        CardProductInsideRow(product, navController)
    }
}

@Composable
fun CardProductInsideRow(product: Product, navController: NavController) {
    var isFavorite = remember(product.id) { mutableStateOf(product.favorite) }

    CardProductDetails(product, product.id, navController)
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

@Composable
fun CardProductDetails(product: Product, productId: String, navController: NavController) {
    Column(modifier = Modifier
        .clickable { navController.navigate(Routes.ProductPage.route + "/${productId}") }
        .padding(start = 5.dp, bottom = 5.dp)) {
        Text(text = product.name, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        val price = product.price
        val euros = price.toInt()
        val cents = ((price - euros) * 100).toInt()

        Row {
            Text(
                text = euros.toString(),
                fontSize = 16.sp
            )
            Text(
                text = "€" + cents.toString().padStart(2, '0'),
                fontSize = 12.sp
            )
        }
    }
}

/* Firebase functions */

suspend fun getCategories(): Map<String, String> {
    var categories = mutableMapOf<String, String>()
    var success = false

    while (!success) {
        try {
            val documents = categoryCollection.get().await()
            for (document in documents) {
                categories[document.id] = document.data[categoryName].toString()
            }
            success = true
        } catch (e: Exception) {
            delay(1000)
            Log.d(ContentValues.TAG, "getCategories: ${e.message}")
        }
    }

    return categories
}

suspend fun getProducts(): Map<String, Product> = suspendCoroutine { continuation ->
    val products = mutableMapOf<String, Product>()

    productCollection.get().addOnSuccessListener { documents ->
        documents.forEach { document ->
            val ddata = document.data
            val productName = getProductName(ddata)
            val productImage = getProductImage(ddata)
            val productCategoryId = getProductCategoryId(ddata)
            val productPrice = getProductPrice(ddata)
            val productDescription = getProductDescription(ddata)

            val favoriteQuery = favoriteCollection
                .whereEqualTo(favoriteUserId, userId)
                .whereEqualTo(favoriteProductId, document.id)

            favoriteQuery.get().addOnSuccessListener { favoriteDocuments ->
                val favorite = !favoriteDocuments.isEmpty
                products[document.id] = Product(
                    productName,
                    productImage,
                    productPrice,
                    productDescription,
                    true,
                    productCategoryId,
                    favorite, document.id
                )

                if (products.size == documents.size()) {
                    allProducts = products
                    continuation.resume(products)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }.addOnFailureListener { exception ->
        continuation.resumeWithException(exception)
    }
}

fun updateFavorite(productId: String) {
    val favoriteQuery = favoriteCollection
        .whereEqualTo(favoriteUserId, userId)
        .whereEqualTo(favoriteProductId, productId)

    favoriteQuery.get().addOnSuccessListener { documents ->
        if (documents.isEmpty) {
            val data = hashMapOf(
                favoriteUserId to userId,
                favoriteProductId to productId
            )
            favoriteCollection.add(data)
        } else {
            documents.forEach { document ->
                favoriteCollection.document(document.id).delete()
            }
        }
    }
}