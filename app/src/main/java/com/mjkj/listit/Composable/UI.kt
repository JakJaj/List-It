package com.mjkj.listit.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
        /** This is a composable function that creates a button WITH a filled background and a label
         *  this button is used as a main action button
         *
         * @param label: String - The text to be displayed on the button
         * @param onClick: () -> Unit - The action to be performed when the button is clicked
         * */
fun ButtonFilled(label: String,onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Composable
        /** This is a composable function that creates a button WITHOUT a filled background and a with a passed label
         *  this button is used as a secondary action button
         *
         * @param label: String - The text to be displayed on the button
         * @param onClick: () -> Unit - The action to be performed when the button is clicked
         **/
fun ButtonTonalFilled(label:String,onClick: () -> Unit) {
    FilledTonalButton(onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Composable
        /** This is a composable function that creates a text field with an outlined border
         *
         *  @param label: String - The text to be displayed on the label
         * */
fun OutlinedTextField(label:String) {
    var text by remember { mutableStateOf("") }

    androidx.compose.material3.OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) }
    )
}

@Composable
        /** This is an AppBar function that creastes an AppBar on top of the screen.
         *
         *
         */
fun ListAppBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Handle hamburger side bar */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            Text(
                text = "List-it",
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp
            )
            IconButton(onClick = { /* TODO: Handle plus icon click */ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}