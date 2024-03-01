package com.mjkj.listit.Activity

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

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background) {
                SignUpContent()
            }
        }
    }
}

@Composable
fun SignUpContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter your name") },
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter your email") },
            modifier = Modifier.padding(10.dp)
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter your password") },
            modifier = Modifier.padding(top = 10.dp, bottom = 150.dp)
        )

        ButtonTonalFilled("Sign Up") {
        }
    }
}
