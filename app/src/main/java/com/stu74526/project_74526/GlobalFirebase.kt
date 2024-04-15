package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.collections.HashMap
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun getDb(): FirebaseFirestore {
    return FirebaseFirestore.getInstance()
}

val userCollection get() = getDb().collection(userRef)
val productCollection get() = getDb().collection(productRef)
val orderCollection get() = getDb().collection(orderRef)
val categoryCollection get() = getDb().collection(categoryRef)
val productCartCollection get() = getDb().collection(productCartRef)
val productOrderCollection get() = getDb().collection(productOrderRef)
val favoriteCollection get() = getDb().collection(favoriteRef)


var currentUser: FirebaseUser? = null

var userId: String = ""

const val userRef = "user"
const val productRef = "product"
const val orderRef = "order"
const val categoryRef = "category"
const val productCartRef = "productcart"
const val productOrderRef = "productorder"
const val favoriteRef = "favorite"


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

// Favorite Strings
val favoriteUserId = "user_id"
val favoriteProductId = "product_id"

// Favorite Get Values

fun getFavoriteUserId(data: MutableMap<String, Any>): String {
    return data[favoriteUserId] as String
}

fun getFavoriteProductId(data: MutableMap<String, Any>): String {
    return data[favoriteProductId] as String
}

// Product Get Values
fun getProductName(data: MutableMap<String, Any>): String {
    return data[productName] as String
}

fun getProductPrice(data: MutableMap<String, Any>): Double {
    return data[productPrice].toString().toDouble()
}

fun getProductDescription(data: MutableMap<String, Any>): String {
    return data[productDescription] as String
}

fun getProductImage(data: MutableMap<String, Any>): String {
    return data[productImage] as String
}

fun getProductCategoryId(data: MutableMap<String, Any>): String {
    return data[productCategoryId] as String
}

fun addUser(data: HashMap<String, String>) {
    userCollection.add(data)
        .addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
}

fun addProductCart(data: HashMap<String, Any?>) {
    productCartCollection.add(data)
        .addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
}

fun updateProductCart(data: HashMap<String, Any?>) {
    productCartCollection.whereEqualTo(productCartUserId, userId)
        .whereEqualTo(productCartProductId, data[productCartProductId])
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                productCartCollection.document(document.id).update(data)
            }
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error updating document", e)
        }
}

suspend fun getProductCart(): MutableMap<String, Int> = suspendCoroutine { continuation ->
    val productsCart = mutableMapOf<String, Int>()

    productCartCollection.whereEqualTo(productCartUserId, userId).get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                Log.d(
                    ContentValues.TAG,
                    "${document.data[productCartProductId]} => ${document.data[productCartQuantity]}"
                )
                val quantity = document.data[productCartQuantity]?.toString()?.toIntOrNull()
                if (quantity != null) {
                    productsCart[document.data[productCartProductId].toString()] = quantity
                    productCollection.document(document.data[productCartProductId].toString()).get()
                        .addOnSuccessListener { product ->
                            val oui = product.data?.let { getProductPrice(it) }?.times(quantity)
                            if (oui != null) {
                                totalCart += oui
                            }
                        }

                } else {
                    Log.d(ContentValues.TAG, "productCartQuantity is not an Int")
                }
            }
            continuation.resume(productsCart)
        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents.", exception)
            continuation.resumeWithException(exception)
        }
}

fun removeProductCart(productId: String) {
    productCartCollection.whereEqualTo(productCartUserId, userId)
        .whereEqualTo(productCartProductId, productId)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                productCartCollection.document(document.id).delete()
            }
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error deleting document", e)
        }
}