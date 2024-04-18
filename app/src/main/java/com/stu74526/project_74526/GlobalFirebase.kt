package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.MutableDoubleState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
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
var userPhone = "phone"
val userUserId = "user_id"
var userFirstName = "first_name"
var userLastName = "last_name"
var userNumber = "number"
var userStreet = "street"
var userCity = "city"
var userZipcode = "zipcode"
var userLat = "lat"
var userLng = "long"

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

// User Get Values
fun getUserUsername(data: MutableMap<String, Any>): String {
    return data[userUsername] as String
}

fun getUserEmail(data: MutableMap<String, Any>): String {
    return data[userEmail] as String
}

fun getUserPhone(data: MutableMap<String, Any>): String {
    return data[userPhone] as String
}

fun getUserId(data: MutableMap<String, Any>): String {
    return data[userUserId] as String
}

fun getUserFirstName(data: MutableMap<String, Any>): String {
    return data[userFirstName] as String
}

fun getUserLastName(data: MutableMap<String, Any>): String {
    return data[userLastName] as String
}

fun getUserNumber(data: MutableMap<String, Any>): String {
    return data[userNumber] as String
}

fun getUserStreet(data: MutableMap<String, Any>): String {
    return data[userStreet] as String
}

fun getUserCity(data: MutableMap<String, Any>): String {
    return data[userCity] as String
}

fun getUserZipcode(data: MutableMap<String, Any>): String {
    return data[userZipcode] as String
}

fun getUserLat(data: MutableMap<String, Any>): String {
    return data[userLat] as String
}

fun getUserLng(data: MutableMap<String, Any>): String {
    return data[userLng] as String
}

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

// Product Order Get Values
fun getProductOrderQuantity(data: MutableMap<String, Any>): Int {
    return data[productOrderQuantity].toString().toInt()
}

fun getProductOrderTotal(data: MutableMap<String, Any>): Double {
    return data[productOrderTotal].toString().toDouble()
}

fun getProductOrderProductId(data: MutableMap<String, Any>): String {
    return data[productOrderProductId] as String
}

// Order get values

fun getOrderTotal(data: MutableMap<String, Any>): Double {
    return data[orderTotal].toString().toDouble()
}

fun getOrderDate(data: MutableMap<String, Any>): com.google.firebase.Timestamp {
    return data[orderDate] as com.google.firebase.Timestamp
}

fun getOrdrerUserId(data: MutableMap<String, Any>): String {
    return data[orderUserId] as String
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
                    sizeProduct.intValue += 1
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

fun addOrder(orderData: HashMap<String, Any?>, totalM: MutableDoubleState) {
    orderCollection.add(orderData)
        .addOnSuccessListener { documentReference ->
            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            val newOrder = Order(totalCart, productsCart, com.google.firebase.Timestamp(Date()))
            var mut = orders.toMutableMap()
            mut[documentReference.id] = newOrder
            orders = mut
            addProductOrder(documentReference.id, totalM)
        }
        .addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
}

fun addProductOrder(id: String, totalM: MutableDoubleState) {
    productsCart.forEach()
    {
        val productOrderData: HashMap<String, Any?> = hashMapOf(
            productOrderOrderId to id,
            productOrderProductId to it.key,
            productOrderQuantity to it.value,
            productOrderTotal to it.value * (allProducts[it.key]?.price ?: 0.0)
        )
        productOrderCollection.add(productOrderData)
        removeProductCart(it.key)
        productsCart = mutableMapOf()
        totalCart = 0.0
        totalM.doubleValue = totalCart
    }
}

suspend fun getOrders(): MutableMap<String, Order> = suspendCoroutine { continuation ->
    val ordersMut = mutableMapOf<String, Order>()

    try {
        orderCollection.whereEqualTo(FieldPath.of(orderUserId), userId).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val productOrderMap = emptyMap<String, Int>().toMutableMap()
                    productOrderCollection.whereEqualTo(productOrderOrderId, document.id).get()
                        .addOnSuccessListener { productOrders ->
                            for (productOrder in productOrders) {
                                val data = productOrder.data
                                productOrderMap[getProductOrderProductId(data)] =
                                    getProductOrderQuantity(data)
                            }
                        }
                    val ddata = document.data

                    ordersMut[document.id] = Order(
                        getOrderTotal(ddata),
                        productOrderMap,
                        getOrderDate(ddata)
                    )
                }
                continuation.resume(ordersMut)
            }.addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
                continuation.resume(ordersMut)
            }
    } catch (e: Exception) {
        Log.w(ContentValues.TAG, "Error getting documents.", e)
        continuation.resume(ordersMut)
    }
}

fun updateUser(data: HashMap<String, Any?>) {
    userCollection.document(userId).update(data)
}