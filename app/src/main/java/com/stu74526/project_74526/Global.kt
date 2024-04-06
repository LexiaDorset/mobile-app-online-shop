package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

val db = FirebaseFirestore.getInstance()

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