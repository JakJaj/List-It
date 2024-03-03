package com.mjkj.listit.Activity

import android.content.ContentValues.TAG
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mjkj.listit.Composable.ButtonFilled
import com.mjkj.listit.Composable.ButtonTonalFilled

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Surface (modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "List It",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 120.sp
                    )

                    Spacer(modifier = Modifier.padding(100.dp))

                    ButtonFilled("Log in"){
                        val intent = Intent(this@MainActivity, LogInActivity::class.java)
                        startActivity(intent)
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    ButtonTonalFilled("Register") {
                        val Intent = Intent(this@MainActivity, SignUpActivity::class.java)
                        startActivity(Intent)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Surface (modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "List It",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 120.sp
            )

            Spacer(modifier = Modifier.padding(100.dp))

            ButtonFilled("Log In") {
            }
            Spacer(modifier = Modifier.padding(5.dp))
            ButtonTonalFilled("Sign Up") {
                //TODO: Add Sign In Logic
            }
        }
    }
}