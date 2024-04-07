package com.stu74526.project_74526

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        currentUser = FirebaseAuth.getInstance().currentUser
        setContent {
            Navigation()
            Log.d(ContentValues.TAG, "onCreate: ${currentUser?.email}")
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
        if(email == "" || password == ""){
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
                    val user = auth.currentUser
                    navController.navigate(Routes.HomePage.route)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    public fun createAccount(email: String, password: String, username : String, navController: NavController) {
        // [START create_user_with_email]
        if(email == "" || password == "" || username == ""){
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
                        "email" to email,
                        "username" to username
                    )
                   addUser(userAdd)
                    navController.navigate(Routes.HomePage.route)
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                     Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }


}
