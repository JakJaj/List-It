package com.mjkj.listit.Composable

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjkj.listit.Activity.FilledListsListActivity

@Composable
        /**
         * Composable function to display an app bar for a list screen.
         *
         * @param activity The title of the activity associated with the app bar.
         */
fun ListAppBar(
    activity: String,
    parentActivity: Activity,
    listOfLists: List<List<String>>,
    inListCode: String? = null,
    listTitle: String? = null,
    listColor: Any? = null,

    ) {
    val listColor = when (listColor) {
        "Red" -> Color(0xFFD91A60)
        "Blue" -> Color(0xFF1862CA)
        "Green" -> Color(0xFF1D893B)
        "Yellow" -> Color(0xFFF6C022)
        "Cyan" -> Color(0xFF139EAE)
        "Pink" -> Color(0xFFE42591)
        "Gray" -> Color(0xFF5F6367)
        "Orange" -> Color(0xFFE87109)
        "Purple" -> Color(0xFF8D32DD)
        "Light Blue" -> Color(0xFF407FEA)
        "Burgundy" -> Color(0xFF870E51)
        "Dark Green" -> Color(0xFF004C3F)
        else -> MaterialTheme.colorScheme.primary
    }

    var appBarText by remember { mutableStateOf(listTitle ?: "List-it") }
    val defaultAppBarText = listTitle ?: "List-it"

    val showDialog = remember {
        mutableStateOf(false)
    }

    val showNavDrawer = remember {
        mutableStateOf(false)
    }

    if ((activity == "EmptyListsListActivity" || activity == "FilledListsListActivity") && showDialog.value) {
        CreateOrJoinDialog(onDismissRequest = { showDialog.value = false }, parentActivity)
    }

    if ((activity == "EmptyListsListActivity" || activity == "FilledListsListActivity" || activity == "EmptyTasksTaskActivity" || activity == "FilledTasksTaskActivity") && showNavDrawer.value) {
        if (inListCode != null) {
            NavDrawer(parentActivity, listOfLists = listOfLists, listCode = inListCode)
        } else {
            NavDrawer(parentActivity, listOfLists = listOfLists)
        }
    }

    if ((activity == "EmptyTasksTaskActivity" || activity == "FilledTasksTaskActivity") && showDialog.value) {
        if (inListCode != null) {
            CreateTaskDialog(
                onDismissRequest = { showDialog.value = false },
                listCode = inListCode,
                parentActivity = parentActivity,
                navDrawerList = listOfLists
            )
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = listColor ?: MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                showNavDrawer.value = changeState(showNavDrawer)
                appBarText = if (appBarText == defaultAppBarText) "TEST" else defaultAppBarText
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Home",
                    tint = Color.White
                )
            }
            Text(
                text = appBarText,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            if (appBarText == "TEST") {
                                parentActivity.startActivity(
                                    Intent(
                                        parentActivity,
                                        FilledListsListActivity::class.java
                                    )
                                )
                            }
                        }
                    },
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

@Composable
        /**
         * Special function to display an Settings app bar for a list screen.
         *
         * @param parentActivity The title of the parent activity associated with the app bar.
         */
fun SettingsAppBar(
    parentActivity: Activity
) {
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
            IconButton(onClick = {
                parentActivity.finish()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Home",
                    tint = Color.White
                )
            }
            Text(
                text = "Settings",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp
            )

            IconButton(enabled = false, onClick = {
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "To nasza słodka tajemnica :)",
                    tint = Color.Transparent
                )
            }
        }
    }
}