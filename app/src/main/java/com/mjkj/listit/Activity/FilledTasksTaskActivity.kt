package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mjkj.listit.Composable.ListAppBar
import com.mjkj.listit.Composable.ListItem
import com.mjkj.listit.Composable.ListItemData
import com.mjkj.listit.Composable.TaskItem
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class FilledTasksTaskActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val listCode: String = intent.getStringExtra("listCode").toString()
            val listColor: String = intent.getStringExtra("listColor").toString()
            val listTitle: String = intent.getStringExtra("listTitle").toString()
            val listItemData: ListItemData? = intent.getParcelableExtra("navDrawerList")
            val listOfLists: List<List<String>> =
                if (listColor != null && listTitle != null && listItemData != null) {
                    listItemData.navDrawerList
                } else {
                    emptyList()
                }

            val db = Firebase.firestore
            val auth = Firebase.auth
            val listOfTasksCodes = mutableListOf<String>()
            val listOfTasks = mutableStateListOf<MutableList<String>>()
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                coroutineScope.launch {

                    val currentListRef = db.collection("lists").document(listCode)
                    currentListRef.get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                Log.d(
                                    "FilledTasksTaskActivity",
                                    "DocumentSnapshot data: ${document.data!!.get("tasks")}"
                                )
                                listOfTasksCodes.addAll(document.data!!.get("tasks") as List<String>)
                                Log.d(
                                    "FilledTasksTaskActivity",
                                    "List of codes: ${listOfTasksCodes.size}"
                                )

                                for (code in listOfTasksCodes) {
                                    val listRef = db.collection("tasks").document(code)
                                    listRef.get().addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            Log.d(
                                                "FilledTasksTaskActivity",
                                                "DocumentSnapshot data: ${document.data}"
                                            )
                                            val taskName =
                                                document.data!!.get("taskName").toString()
                                            val description =
                                                document.data!!.get("description").toString()
                                            val creator = document.data!!.get("creator").toString()
                                            val status = document.data!!.get("status").toString()


                                            Log.d(
                                                "FilledTasksTaskActivity",
                                                "Task name: $taskName, description: $description, creator: $creator, status: $status"
                                            )
                                            val tempList =
                                                mutableListOf(taskName, description, status, code)
                                            listOfTasks.add(tempList)
                                            Log.d(
                                                "FilledTasksTaskActivity",
                                                "Temp lists: $tempList"
                                            )
                                            Log.d(
                                                "FilledTasksTaskActivity",
                                                "List of lists: $listOfTasks"
                                            )
                                        } else {
                                            Log.d("FilledTasksTaskActivity", "No such document")
                                        }
                                    }.addOnFailureListener { exception ->
                                        Log.d(
                                            "FilledTasksTaskActivity",
                                            "get failed with ",
                                            exception
                                        )
                                    }
                                }
                            } else {
                                Log.d("FilledTasksTaskActivity", "No such document")
                            }

                        }.addOnFailureListener { exception ->
                            Log.d("FilledTasksTaskActivity", "get failed with ", exception)
                        }
                    currentListRef.addSnapshotListener(this@FilledTasksTaskActivity) { value, error ->
                        if (error != null) {
                            Log.w("FilledTasksTaskActivity", "Listen failed.", error)
                            return@addSnapshotListener
                        }
                        val source = if (value != null && value.metadata.hasPendingWrites()) {
                            "Local"
                        } else {
                            "Server"
                        }
                        if (value != null && value.exists()) {
                            Log.d("FilledTasksTaskActivity", "$source data: ${value.data}")
                        } else {
                            Log.d("FilledTasksTaskActivity", "$source data: null")
                        }
                    }
                }
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    topBar = {
                        ListAppBar(
                            activity = "FilledTasksTaskActivity",
                            this,
                            listOfLists,
                            listCode,
                            listTitle,
                            listColor
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))
                        LazyColumn {
                            items(listOfTasks.size) { i ->
                                TaskItem(
                                    title = listOfTasks[i][0],
                                    context = this@FilledTasksTaskActivity,
                                    code = listOfTasks[i][3],
                                    listOfLists
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
