package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.OutlinedPasswordTextField
import com.mjkj.listit.Composable.SettingsAppBar
import com.mjkj.listit.ui.theme.AppSettings
import com.mjkj.listit.ui.theme.AppTheme

class SettingsActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicjalizacja ustawień aplikacji
        AppSettings.init(applicationContext)

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

                            Spacer(modifier = Modifier.padding(15.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "App theme",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )

                                Switch(
                                    checked = AppSettings.darkMode,
                                    onCheckedChange = { newDarkMode -> AppSettings.darkMode = newDarkMode
                                        restartApp()
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.padding(15.dp))

                            HorizontalDivider()

                            Spacer(modifier = Modifier.padding(20.dp))

                            Text(
                                text = "Reset Password",
                                style = MaterialTheme.typography.displayLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 40.sp
                            )

                            val currentPassword: String = OutlinedPasswordTextField("Current Password")
                            Spacer(modifier = Modifier.padding(10.dp))
                            val password: String = OutlinedPasswordTextField("New Password")
                            Spacer(modifier = Modifier.padding(10.dp))
                            val retypedPassword = OutlinedPasswordTextField("Retype New Password")

                            Spacer(modifier = Modifier.padding(20.dp))

                            ButtonTonalFilled(label = "Confirm") {
                                changePassword(currentPassword, password, retypedPassword, this@SettingsActivity)
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Function to restart the application
     */
    private fun restartApp() {
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        applicationContext.startActivity(intent)
        finishActivity(0)
    }
}

/**
 * Function to check if the two passwords are the same
 * @param firstPassword: The first password
 * @param secondPassword: The second password
 * @return Boolean: True if the passwords are the same, false otherwise
 */
fun checkIfPasswordAreTheSame(firstPassword:String, secondPassword:String):Boolean{
    return firstPassword == secondPassword
}

/**
 * Function to check if the password matches the current one
 * @param currentPassword: The current password
 * @param onResult: The function to execute after the check
 */
fun checkIfPasswordMatchTheCurrentOne(currentPassword: String, onResult: (Boolean) -> Unit) {
    val auth: FirebaseAuth = Firebase.auth
    val currentUser = auth.currentUser
    val credential = EmailAuthProvider.getCredential(currentUser?.email!!, currentPassword)

    currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
        onResult(task.isSuccessful)
    }
}
/**
 * Function to change the password
 * @param currentPassword: The current password
 * @param firstPassword: The new password
 * @param secondPassword: The new password retyped
 * @param parentActivity: The activity where the function is called
 */
fun changePassword(currentPassword:String, firstPassword: String, secondPassword: String, parentActivity:Activity){
    if(checkIfPasswordAreTheSame(firstPassword, secondPassword)){
        checkIfPasswordMatchTheCurrentOne(currentPassword) { isMatch ->
            if(isMatch){
                val auth: FirebaseAuth = Firebase.auth
                val currentUser = auth.currentUser
                currentUser?.updatePassword(firstPassword)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(parentActivity, "Password changed successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(parentActivity, "Current password is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
    else{
        Toast.makeText(parentActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
    }
}