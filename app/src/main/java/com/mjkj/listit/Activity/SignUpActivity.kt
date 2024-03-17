package com.mjkj.listit.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mjkj.listit.Composable.ButtonFilled
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.OutlinedPasswordTextField
import com.mjkj.listit.Composable.OutlinedTextField

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val db = Firebase.firestore
            val auth: FirebaseAuth = Firebase.auth

            val currentUser = auth.currentUser
            if(currentUser != null){
                val currentUserR = db.collection("users").document(auth.currentUser?.uid!!)
                currentUserR.get().addOnSuccessListener { documentSnapchot ->

                    if(documentSnapchot.exists()){
                        Log.d("LogInActivity", "DocumentSnapshot data: ${documentSnapchot.data}")
                        val lists = documentSnapchot.get("lists") as? MutableList<String>

                        if(lists == null){
                            Log.d("LogInActivity", "No lists go to empty lists activity")
                            val intent = Intent(this@SignUpActivity, ListsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Log.d("LogInActivity", "Lists exist go to lists activity")
                            val intent = Intent(this@SignUpActivity, ScrollListsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    else{
                        Log.d("LogInActivity", "No such document")
                    }
                }.addOnFailureListener { exception ->
                    Log.d("LogInActivity", "get failed with ", exception)
                }
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background) {

                Column {

                    Column (modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){

                        val username:String = OutlinedTextField("Username")
                        Spacer(modifier = Modifier.padding(10.dp))
                        val email:String = OutlinedTextField("Email")
                        Spacer(modifier = Modifier.padding(10.dp))
                        val password:String = OutlinedPasswordTextField("Password")
                        Spacer(modifier = Modifier.padding(10.dp))
                        val retypedPassword = OutlinedPasswordTextField("Confirm password")

                        Spacer(modifier = Modifier.padding(60.dp))

                        ButtonFilled("Register") {

                            if(password != retypedPassword){
                                Log.d("SignUpActivity", "Passwords do not match")
                                Toast.makeText(this@SignUpActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                            }
                            else if(username.isEmpty() || email.isEmpty() || password.isEmpty() || retypedPassword.isEmpty()){
                                Log.d("SignUpActivity", "Please fill in all fields")
                                Toast.makeText(this@SignUpActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            }
                            else if(password.length < 6){
                                Log.d("SignUpActivity", "Password must be at least 6 characters long")
                                Toast.makeText(this@SignUpActivity, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this@SignUpActivity){ task ->
                                        if(task.isSuccessful){
                                            Log.d("SignUpActivity", "User created successfully")
                                            val user = hashMapOf(
                                                "username" to username,
                                                "email" to email,
                                                "lists" to null
                                            )
                                            db.collection("users").document("${auth.currentUser?.uid}")
                                                .set(user)
                                                .addOnSuccessListener { Log.d("SignUpActivity", "DocumentSnapshot successfully written!") }
                                                .addOnFailureListener { e -> Log.w("SignUpActivity", "Error writing document", e) }
                                            val intent = Intent(this@SignUpActivity, ListsActivity::class.java)
                                            startActivity(intent)
                                        }
                                        else{
                                            Log.d("SignUpActivity", "User creation failed")
                                            Toast.makeText(this@SignUpActivity, "User creation failed", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        ButtonTonalFilled(label = "Go back"){
                            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}
