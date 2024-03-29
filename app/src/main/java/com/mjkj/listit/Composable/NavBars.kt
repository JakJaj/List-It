package com.mjkj.listit.Composable

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.mjkj.listit.Activity.EmptyTasksTaskActivity
import com.mjkj.listit.Activity.FilledTasksTaskActivity
import com.mjkj.listit.Activity.ListsSettings
import com.mjkj.listit.Activity.MainActivity
import com.mjkj.listit.Activity.SettingsActivity


@Composable
fun NavDrawer(parentActivity: Activity, listOfLists: List<List<String>>, listCode: String? = null) {
    ModalDrawerSheet() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with the text "List it"
            Spacer(modifier = Modifier.height(100.dp))
            // Middle section with ListView
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(listOfLists.size) { i ->
                    NavDrawerItem(
                        title = listOfLists[i][0],
                        description = listOfLists[i][1],
                        color = listOfLists[i][2],
                        context = parentActivity,
                        code = listOfLists[i][3],
                        listCode,
                        listOfLists
                    )
                }
            }
            // Bottom section with buttons
            HorizontalDivider()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonTonalFilled(label = "Settings") {
                    if(listCode != null){
                        val intent = Intent(parentActivity, ListsSettings::class.java)
                        intent.putExtra("listCode", listCode)
                        parentActivity.startActivity(intent)
                    }
                    else{
                        val intent = Intent(parentActivity, SettingsActivity::class.java)
                        parentActivity.startActivity(intent)
                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonTonalFilled(label = "Log out") {
                    val intent = Intent(parentActivity, MainActivity::class.java)
                    ContextCompat.startActivity(parentActivity, intent, null)
                    Firebase.auth.signOut()
                    parentActivity.finish()
                }
            }
        }
    }
}

@Composable
fun NavDrawerItem(
    title: String,
    description: String,
    color: String,
    context: Context,
    code: String,
    listCode: String? = null,
    navDrawerList: List<List<String>>
) {
    var backgroundColor = if (code == listCode) Color.LightGray else Color.Transparent
    if(backgroundColor == Color.LightGray){
        if(isSystemInDarkTheme()){
            backgroundColor = MaterialTheme.colorScheme.onSecondary
        }
        else{
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer

        }
    }
    val firestore = Firebase.firestore
    val circleColor = when (color) {
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
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                firestore
                    .collection("lists")
                    .document(code)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {

                            Log.d(
                                "FilledTasksTaskActivity",
                                "List of codes: ${document.data!!.get("tasks")}"
                            )

                            if (document.data!!.get("tasks") == null) {
                                val intent = Intent(context, EmptyTasksTaskActivity::class.java)
                                intent.putExtra("listCode", code)
                                intent.putExtra("listColor", color)
                                intent.putExtra("listTitle", title)
                                intent.putExtra("navDrawerList", ListItemData(navDrawerList))
                                context.startActivity(intent)
                            } else {
                                val intent = Intent(context, FilledTasksTaskActivity::class.java)
                                intent.putExtra("listCode", code)
                                intent.putExtra("listColor", color)
                                intent.putExtra("listTitle", title)
                                intent.putExtra("navDrawerList", ListItemData(navDrawerList))
                                context.startActivity(intent)
                            }
                        }
                    }
            }
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(circleColor, shape = CircleShape)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 10.sp
                )
            }
        }
    }
}