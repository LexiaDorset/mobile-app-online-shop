package com.stu74526.project_74526

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        currentUser = FirebaseAuth.getInstance().currentUser
        // Get the good user with user_id in userCollection
        //Log.d(ContentValues.TAG, "onCreate: ${currentUser?.email}")
        if (currentUser != null) {
            val userId_ = currentUser!!.uid
            userCollection.whereEqualTo(userUserId, userId_).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        userId = document.id
                        var ddata = document.data
                        currentUserClass = User(
                            username = getUserUsername(ddata),
                            firstName = getUserFirstName(ddata),
                            lastName = getUserLastName(ddata),
                            email = getUserEmail(ddata),
                            phone = getUserPhone(ddata),
                            number = getUserNumber(ddata),
                            street = getUserStreet(ddata),
                            city = getUserCity(ddata),
                            zipCode = getUserZipcode(ddata),
                            imageP = "https://thispersondoesnotexist.com/image",
                            lat = getUserLat(ddata),
                            lng = getUserLng(ddata),
                        )
                        collectionsSet = true
                        usernameSign = currentUserClass.username
                        firstNameSign = currentUserClass.firstName
                        lastNameSign = currentUserClass.lastName
                        phoneSign = currentUserClass.phone
                        citySign = currentUserClass.city
                        numberSign = currentUserClass.number
                        streetSign = currentUserClass.street
                        zipcodeSign = currentUserClass.zipCode
                        latSign = currentUserClass.lat
                        lgnSign = currentUserClass.lng
                        return@addOnSuccessListener
                    }
                }
        }
        setContent {
            var isNavigationReady by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                products = getProducts()
                if (currentUser != null) {
                    productsCart = getProductCart()
                    orders = getOrders()
                }
                isNavigationReady = true
            }

            if (isNavigationReady) {
                Navigation()
            }
        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {
    }

    public fun signIn(email: String, password: String, navController: NavController) {
        // [START sign_in_with_email]
        if (email == "" || password == "") {
            Toast.makeText(
                baseContext,
                "Please enter email and password",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    currentUser = auth.currentUser
                    userCollection.whereEqualTo(userUserId, currentUser!!.uid).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                userId = document.id
                                var ddata = document.data
                                currentUserClass = User(
                                    username = getUserUsername(ddata),
                                    firstName = getUserFirstName(ddata),
                                    lastName = getUserLastName(ddata),
                                    email = getUserEmail(ddata),
                                    phone = getUserPhone(ddata),
                                    number = getUserNumber(ddata),
                                    street = getUserStreet(ddata),
                                    city = getUserCity(ddata),
                                    zipCode = getUserZipcode(ddata),
                                    imageP = "https://thispersondoesnotexist.com/image",
                                    lat = getUserLat(ddata),
                                    lng = getUserLng(ddata),
                                )
                                usernameSign = currentUserClass.username
                                firstNameSign = currentUserClass.firstName
                                lastNameSign = currentUserClass.lastName
                                phoneSign = currentUserClass.phone
                                citySign = currentUserClass.city
                                numberSign = currentUserClass.number
                                streetSign = currentUserClass.street
                                zipcodeSign = currentUserClass.zipCode
                                latSign = currentUserClass.lat
                                lgnSign = currentUserClass.lng
                                return@addOnSuccessListener
                            }
                        }
                    navController.navigate(Routes.HomePage.route)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        // [END sign_in_with_email]
    }

    public fun createAccount(
        email: String,
        password: String,
        username: String,
        firstName: String,
        lastName: String,
        phone: String,
        number: String,
        street: String, city: String,
        zipcode: String,
        lat: String,
        lont: String,
        navController: NavController
    ) {
        // [START create_user_with_email]
        if (email == "" || password == "" || username == "") {
            Toast.makeText(
                baseContext,
                "Please enter email, username and password",
                Toast.LENGTH_SHORT,
            ).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val userAdd = hashMapOf(
                        userEmail to email,
                        userUsername to username,
                        userUserId to user!!.uid,
                        userFirstName to firstName,
                        userLastName to lastName,
                        userPhone to phone,
                        userCity to city,
                        userNumber to number,
                        userStreet to street,
                        userZipcode to zipcode,
                        userLat to lat,
                        userLng to lont
                    )
                    addUser(userAdd)
                    navController.navigate(Routes.HomePage.route)
                    currentUserClass = User(
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phone = phone,
                        street = street,
                        number = number,
                        city = city,
                        zipCode = zipcode,
                        imageP = "https://thispersondoesnotexist.com/image",
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        // [END create_user_with_email]
    }

    fun signOut(navController: NavController) {
        auth.signOut()
        currentUserClass = User()
        userId = null.toString()
        currentUser = null
        usernameSign = ""
        firstNameSign = ""
        lastNameSign = ""
        phoneSign = ""
        citySign = ""
        numberSign = ""
        streetSign = ""
        zipcodeSign = ""
        latSign = ""
        lgnSign = ""
        navController.navigate(Routes.LoginPage.route)
    }
}
