package com.mjkj.listit.Composable

import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp


@Composable
fun ButtonFilled(label: String,onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Composable
fun ButtonTonalFilled(label:String,onClick: () -> Unit) {
    FilledTonalButton(onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Composable
fun OutlinedTextField(label:String) {
    var text by remember { mutableStateOf("") }

    androidx.compose.material3.OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) }
    )
}