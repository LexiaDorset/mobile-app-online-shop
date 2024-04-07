package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

val db = FirebaseFirestore.getInstance()

var allProducts by mutableStateOf(emptyMap<String, Product>())

var currentUser : FirebaseUser? = null


const val userRef = "user"
const val productRef = "product"
const val orderRef = "order"
const val categoryRef = "category"
const val productCartRef = "productcart"
const val productOrderRef = "productorder"

val userCollection = db.collection(userRef)
val productCollection = db.collection(productRef)
val orderCollection = db.collection(orderRef)
val categoryCollection = db.collection(categoryRef)
val productCartCollection = db.collection(productCartRef)
val productOrderCollection = db.collection(productOrderRef)

// User Strings

val userUsername = "username"
val userEmail = "email"
val userPhone = "phone"
val userUserId = "user_id"
val userFirstName = "first_name"
val userLastName = "last_name"
val userNumber = "number"
val userStreet = "street"
val userCity = "city"
val userZipcode = "zipcode"
val userLat = "lat"
val userLng = "lng"

// Product Strings

val productName = "name"
val productPrice = "price"
val productDescription = "description"
val productImage = "image"
val productCategoryId = "category_id"
val productAvailable = "available"

// Order Strings
val orderUserId = "user_id"
val orderTotal = "total"
val orderDate = "date"

// Category Strings
val categoryName = "name"

// Product Cart Strings
val productCartUserId = "user_id"
val productCartProductId = "product_id"
val productCartQuantity = "quantity"
val productCartTotal = "total"

// Product Order Strings
val productOrderOrderId = "order_id"
val productOrderProductId = "product_id"
val productOrderQuantity = "quantity"
val productOrderTotal = "total"


// Product Get Values
fun getProductName(data: MutableMap<String, Any>) : String
{
    return data[productName] as String
}

fun getProductPrice(data: MutableMap<String, Any>) : Double
{
    return data[productPrice] as Double
}

fun getProductDescription(data: MutableMap<String, Any>) : String
{
    return data[productDescription] as String
}

fun getProductImage(data: MutableMap<String, Any>) : String
{
    return data[productImage] as String
}

fun getProductCategoryId(data: MutableMap<String, Any>) : String
{
    return data[productCategoryId] as String
}

fun addUser(data : HashMap<String, String>)
{
    userCollection.add(data)
        .addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
}

@Composable
fun ShowImage(drawable: Int, modifier: Modifier = Modifier) // Show image
{
    Image(
        painter = painterResource(id = drawable),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun ShowImageString(drawable: String, modifier: Modifier = Modifier) // Show image
{
    Image(
        painter = painterResource(id = LocalContext.current.resources.getIdentifier(drawable,
            "drawable", "com.stu74526.project_74526")),
        contentDescription = null,
        modifier = modifier
    )
}


@Composable
fun ShowImageScale(
    draw: Int, modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    Image(
        painter = painterResource(id = draw),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}
