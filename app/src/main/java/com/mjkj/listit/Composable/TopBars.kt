package com.mjkj.listit.Composable

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
        /**
         * Composable function to display an app bar for a list screen.
         *
         * @param activity The title of the activity associated with the app bar.
         */
fun ListAppBar(
    activity: String,
    test: Activity,
    listOfLists: List<List<String>>
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showNavDrawer = remember {
        mutableStateOf(false)
    }

    if ( (activity == "ListActivity" || activity == "ScrollListActivity")  && showDialog.value) {
        CreateOrJoinDialog(onDismissRequest = { showDialog.value = false },test)
    }

    if ((activity == "ListActivity" || activity == "ScrollListActivity") && showNavDrawer.value) {
        NavDrawer(test, listOfLists = listOfLists)
    }

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
            IconButton(onClick = { showNavDrawer.value = changeState(showNavDrawer) }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Home",
                    tint = Color.White
                )
            }
            Text(
                text = "List-it",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp
            )
            IconButton(onClick = { showDialog.value = changeState(showDialog) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}