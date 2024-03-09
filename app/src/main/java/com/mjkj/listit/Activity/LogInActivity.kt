package com.mjkj.listit.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mjkj.listit.Composable.ButtonFilled
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.OutlinedTextField

class LogInActivity: ComponentActivity(){
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentUser = auth.currentUser
            if(currentUser != null){
                val intent = Intent(this@LogInActivity, ListsActivity::class.java)
                startActivity(intent)
                finish()
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background) {

                Column {

                    Column (modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){

                        var username:String = OutlinedTextField("Username")
                        Spacer(modifier = Modifier.padding(10.dp))
                        var password:String = OutlinedTextField("Password")

                        Spacer(modifier = Modifier.padding(60.dp))

                        ButtonFilled("Log in") {
                            //TODO: Verify login using firebase

                            val intent = Intent(this@LogInActivity, ListsActivity::class.java)
                            startActivity(intent)
                            Log.d("D", "Username: $username")
                            Log.d("D", "Password: $password")
                            finish()
                            

                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        ButtonTonalFilled(label = "Go back"){
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


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {

        Column {

            Column (modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){

                OutlinedTextField("Username")
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField("Password")

                Spacer(modifier = Modifier.padding(60.dp))

                ButtonFilled("Log in") {
                    //TODO: Handle login
                }
                ButtonTonalFilled(label = "Go back"){
                    //TODO: Handle going back to main screen
                }
            }
        }
    }
}
