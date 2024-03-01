package com.mjkj.listit.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Surface (modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background){
                Column {


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "List It",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        ButtonFilled("Log In") {
                            //TODO: Add Login Logic
                        }
                        ButtonTonalFilled("Sign Up") {
                            //TODO: Add Sign In Logic
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonFilled(label: String,onClick: () -> Unit) {
    Button(modifier = Modifier.padding(bottom = 5.dp, top = 200.dp), onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Composable
fun ButtonTonalFilled(label:String,onClick: () -> Unit) {
    FilledTonalButton(modifier = Modifier.padding(5.dp),onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Surface (modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background){
        Column {


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "List It",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 100.sp
                )

                ButtonFilled("Log In") {
                    //TODO: Add Login Logic
                }
                ButtonTonalFilled("Sign Up") {
                    //TODO: Add Sign In Logic
                }
            }
        }
    }
}