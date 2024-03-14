package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mjkj.listit.Composable.ListAppBar
import kotlinx.coroutines.launch

class ScrollListsActivity : ComponentActivity() {

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
                                            Log.d(
                                                "LogInActivity",
                                                "List name: $listName description: $description color: $color"
                                            )
                                            val tempList =
                                                mutableListOf(listName, description, color)
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
                    currentUserRef.addSnapshotListener(this@ScrollListsActivity) { value, error ->
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
                            activity = "ScrollListActivity",
                            this
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
                                    context = this@ScrollListsActivity
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(title: String, description: String, color: String, context: Context) {
    val backgroundColor = when (color) {
        "Red" -> Color.Red
        "Blue" -> Color.Blue
        "Green" -> Color.Green
        "Yellow" -> Color.Yellow
        "Cyan" -> Color.Cyan
        "Pink" -> Color.Magenta
        "White" -> Color.White
        "Gray" -> Color.Gray
        else -> Color.Transparent
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                val intent = Intent(context, ListActivity::class.java)
                context.startActivity(intent)
            }
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}
