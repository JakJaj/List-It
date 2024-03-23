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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mjkj.listit.Composable.ButtonFilled
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.OutlinedPasswordTextField
import com.mjkj.listit.Composable.OutlinedTextField
import com.mjkj.listit.ui.theme.AppTheme

class LogInActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = Firebase.firestore
            val auth: FirebaseAuth = Firebase.auth
            val currentUser = auth.currentUser

            if (currentUser != null) {
                val currentUserR = db.collection("users").document(auth.currentUser?.uid!!)
                currentUserR.get().addOnSuccessListener { documentSnapchot ->

                    if (documentSnapchot.exists()) {
                        Log.d("LogInActivity", "DocumentSnapshot data: ${documentSnapchot.data}")
                        val lists = documentSnapchot.get("lists") as? MutableList<String>

                        if (lists.isNullOrEmpty()) {
                            Log.d("LogInActivity", "No lists go to empty lists activity")
                            val intent =
                                Intent(this@LogInActivity, EmptyListsListActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.d("LogInActivity", "Lists exist go to lists activity")
                            val intent =
                                Intent(this@LogInActivity, FilledListsListActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.d("LogInActivity", "No such document")
                    }
                }.addOnFailureListener { exception ->
                    Log.d("LogInActivity", "get failed with ", exception)
                }
            }
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column {

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                        val email: String = OutlinedTextField("Email","")
                        Spacer(modifier = Modifier.padding(10.dp))
                        val password: String = OutlinedPasswordTextField(label = "Password")

                            Spacer(modifier = Modifier.padding(60.dp))

                            ButtonFilled("Log in") {
                                //TODO: Verify login using firebase
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this@LogInActivity) { task ->
                                        if (task.isSuccessful) {
                                            val currentUserId = auth.currentUser?.uid
                                            val currentUserRef =
                                                db.collection("users").document(currentUserId!!)
                                            currentUserRef.get()
                                                .addOnSuccessListener { documentSnapchot ->

                                                    if (documentSnapchot.exists()) {
                                                        Log.d(
                                                            "LogInActivity",
                                                            "DocumentSnapshot data: ${documentSnapchot.data}"
                                                        )
                                                        val lists =
                                                            documentSnapchot.get("lists") as? MutableList<String>

                                                        if (lists == null) {
                                                            Log.d(
                                                                "LogInActivity",
                                                                "No lists go to empty lists activity"
                                                            )
                                                            val intent = Intent(
                                                                this@LogInActivity,
                                                                EmptyListsListActivity::class.java
                                                            )
                                                            startActivity(intent)
                                                            finish()
                                                        } else {
                                                            Log.d(
                                                                "LogInActivity",
                                                                "Lists exist go to lists activity"
                                                            )
                                                            val intent = Intent(
                                                                this@LogInActivity,
                                                                FilledListsListActivity::class.java
                                                            )
                                                            startActivity(intent)
                                                            finish()
                                                        }
                                                    } else {
                                                        Log.d("LogInActivity", "No such document")
                                                    }
                                                }.addOnFailureListener { exception ->
                                                    Log.d(
                                                        "LogInActivity",
                                                        "get failed with ",
                                                        exception
                                                    )
                                                }
                                        } else {
                                            Log.w(
                                                "LogInActivity",
                                                "signInWithEmail:failure",
                                                task.exception
                                            )
                                            Toast.makeText(
                                                baseContext,
                                                "Authentication failed.",
                                                Toast.LENGTH_SHORT,
                                            ).show()
                                        }
                                    }
                            }
                            Spacer(modifier = Modifier.padding(5.dp))
                            ButtonTonalFilled(label = "Go back") {
                                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                OutlinedTextField("Username","")
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField("Password","")

                Spacer(modifier = Modifier.padding(60.dp))

                ButtonFilled("Log in") {
                    //TODO: Handle login
                }
                ButtonTonalFilled(label = "Go back") {
                    //TODO: Handle going back to main screen
                }
            }
        }
    }
}
