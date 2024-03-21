package com.mjkj.listit.Composable

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mjkj.listit.Activity.EmptyTasksTaskActivity
import com.mjkj.listit.Activity.FilledTasksTaskActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItemData(val navDrawerList: List<List<String>>) : Parcelable

@Composable
fun ListItem(
    title: String,
    description: String,
    color: String,
    context: Context,
    code: String,
    navDrawerList: List<List<String>>
) {
    val firestore = Firebase.firestore
    val backgroundColor = when (color) {
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
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(
                text = title,
                fontSize = 25.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskItem(
    title: String,
    activity: Activity,
    code: String,
    navDrawerList: List<List<String>>
) {

    val firestore = Firebase.firestore
    val showDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var isChecked: Boolean by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            isChecked = getTasksStatus(code)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                showDialog.value = true
            }
            .background(
                if (isChecked) Color(0xFF004C3F) else Color(0xFFD91A60),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = title,
                    fontSize = 25.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    firestore.collection("tasks").document(code).update("status", isChecked);
                    },
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
        if(showDialog.value){
            taskInfoDialog(onDismissRequest = {showDialog.value = false}, parentActivity = activity, taskCode = code)
        }
    }
}

suspend fun getTasksStatus(code: String):Boolean{
    val document = Firebase.firestore.collection("tasks").document(code).get().await()
    return document.exists() && (document.data?.get("status") as Boolean ?: false)
}