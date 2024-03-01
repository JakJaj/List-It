package com.mjkj.listit.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mjkj.listit.Composable.*

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background) {

                Column {

                    Column (modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){

                        OutlinedTextField("Username")
                        Spacer(modifier = Modifier.padding(10.dp))
                        OutlinedTextField("Email")
                        Spacer(modifier = Modifier.padding(10.dp))
                        OutlinedTextField("Password")
                        Spacer(modifier = Modifier.padding(10.dp))
                        OutlinedTextField("Confirm password")

                        Spacer(modifier = Modifier.padding(60.dp))

                        ButtonFilled("Sign Up") {
                            //TODO: Handle registration
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        ButtonTonalFilled(label = "Go back"){
                            //TODO: Handle going back to main screen
                            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}
