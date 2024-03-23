package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.OutlinedPasswordTextField
import com.mjkj.listit.Composable.SettingsAppBar
import com.mjkj.listit.ui.theme.AppTheme

class SettingsActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            SettingsAppBar(parentActivity = this@SettingsActivity)
                        }
                    ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.padding(40.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Push Notifications",
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            var checked by remember { mutableStateOf(true) }
                            Switch(
                                checked = checked,
                                onCheckedChange = {
                                    checked = it
                                }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "App theme",
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            var checked by remember { mutableStateOf(true) }
                            Switch(
                                checked = checked,
                                onCheckedChange = {
                                    checked = it
                                }
                            )
                        }

                        Spacer(modifier = Modifier.padding(10.dp))

                        HorizontalDivider()

                        Spacer(modifier = Modifier.padding(10.dp))

                        Text(
                            text = "Reset Password",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 40.sp
                        )

                        val email: String = OutlinedPasswordTextField("Current Password")
                        Spacer(modifier = Modifier.padding(10.dp))
                        val password: String = OutlinedPasswordTextField("New Password")
                        Spacer(modifier = Modifier.padding(10.dp))
                        val retypedPassword = OutlinedPasswordTextField("Retype New Password")

                        Spacer(modifier = Modifier.padding(20.dp))

                        ButtonTonalFilled(label = "Confirm") {
                            // TODO: Implement password reset functionality
                        }
                    }
                }
            }
        }
        }
    }
}
