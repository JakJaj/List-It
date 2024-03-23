package com.mjkj.listit.Composable

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


    @Composable
            /** This is a composable function that creates a button WITH a filled background and a label
             *  this button is used as a main action button
             *
             * @param label: String - The text to be displayed on the button
             * @param onClick: () -> Unit - The action to be performed when the button is clicked
             * */
    fun ButtonFilled(label: String, onClick: () -> Unit) {
        Button(onClick = { onClick() }) {
            Text(label, fontSize = 25.sp)
        }
    }

    @Composable
            /** This is a composable function that creates a button WITHOUT a filled background and a with a passed label
             *  this button is used as a secondary action button
             *
             * @param label: String - The text to be displayed on the button
             * @param onClick: () -> Unit - The action to be performed when the button is clicked
             **/
    fun ButtonTonalFilled(label: String, onClick: () -> Unit) {
        FilledTonalButton(onClick = { onClick() }) {
            Text(label, fontSize = 25.sp)
        }
    }

@Composable
fun DangerFilledButton(label: String,color: Color, onClick: () -> Unit) {
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = color)){
        Text(label, fontSize = 25.sp, color = Color.White)
    }
}
