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
import kotlinx.coroutines.launch

class FilledListsListActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = Firebase.firestore
            val auth = Firebase.auth
            val listOfCodes = mutableListOf<String>()
            val listOfLists = mutableStateListOf<MutableList<String>>()
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                coroutineScope.launch {

                    val currentUserId = auth.currentUser?.uid
                    val currentUserRef = db.collection("users").document(currentUserId!!)
                    currentUserRef.get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                Log.d(
                                    "LogInActivity",
                                    "DocumentSnapshot data: ${document.data!!.get("lists")}"
                                )
                                listOfCodes.addAll(document.data!!.get("lists") as List<String>)
                                Log.d("LogInActivity", "List of codes: ${listOfCodes.size}")

                                for (code in listOfCodes) {
                                    val listRef = db.collection("lists").document(code)
                                    listRef.get().addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            Log.d(
                                                "LogInActivity",
                                                "DocumentSnapshot data: ${document.data}"
                                            )
                                            val listName =
                                                document.data!!.get("listName").toString()
                                            val description =
                                                document.data!!.get("description").toString()
                                            val color = document.data!!.get("color").toString()
                                            val code = document.data!!.get("code").toString()
                                            Log.d(
                                                "LogInActivity",
                                                "List name: $listName description: $description color: $color code: $code"
                                            )
                                            val tempList =
                                                mutableListOf(listName, description, color, code)
                                            listOfLists.add(tempList)
                                            Log.d("LogInActivity", "Temp lists: $tempList")
                                            Log.d("LogInActivity", "List of lists: $listOfLists")
                                        } else {
                                            Log.d("LogInActivity", "No such document")
                                        }
                                    }.addOnFailureListener { exception ->
                                        Log.d("LogInActivity", "get failed with ", exception)
                                    }
                                }
                            } else {
                                Log.d("LogInActivity", "No such document")
                            }

                        }.addOnFailureListener { exception ->
                            Log.d("LogInActivity", "get failed with ", exception)
                        }
                    currentUserRef.addSnapshotListener(this@FilledListsListActivity) { value, error ->
                        if (error != null) {
                            Log.w("LogInActivity", "Listen failed.", error)
                            return@addSnapshotListener
                        }
                        val source = if (value != null && value.metadata.hasPendingWrites()) {
                            "Local"
                        } else {
                            "Server"
                        }
                        if (value != null && value.exists()) {
                            Log.d("LogInActivity", "$source data: ${value.data}")
                        } else {
                            Log.d("LogInActivity", "$source data: null")
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
                            activity = "ScrollListsActivity",
                            this,
                            listOfLists,
                            null,
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
                            items(listOfLists.size) { i ->
                                ListItem(
                                    title = listOfLists[i][0],
                                    description = listOfLists[i][1],
                                    color = listOfLists[i][2],
                                    context = this@FilledListsListActivity,
                                    code = listOfLists[i][3]
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


