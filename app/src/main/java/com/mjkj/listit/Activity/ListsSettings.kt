package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.mjkj.listit.Composable.ButtonFilled
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.DangerFilledButton
import com.mjkj.listit.Composable.DropdownMenuBox
import com.mjkj.listit.Composable.ListAppBar
import com.mjkj.listit.Composable.OutlinedTextField
import com.mjkj.listit.Composable.SettingsAppBar
import com.mjkj.listit.Model.ListOfTasks
import com.mjkj.listit.ui.theme.AppTheme
import kotlinx.coroutines.launch

class ListsSettings : ComponentActivity(){


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val colorArray = arrayOf(
                "Red",
                "Blue",
                "Green",
                "Yellow",
                "Cyan",
                "Pink",
                "Gray",
                "Orange",
                "Purple",
                "Light Blue",
                "Burgundy",
                "Dark Green"
            )
            val listCode: String = intent.getStringExtra("listCode").toString()
            val firestore = Firebase.firestore
            val loading = remember { mutableStateOf(true) }
            val listName = remember { mutableStateOf("") }
            val listDescription = remember { mutableStateOf("") }
            val listColor = remember { mutableStateOf("") }
            val coroutineScope = rememberCoroutineScope()

            var errorColorText = MaterialTheme.colorScheme.error
            var errorColorContainer = MaterialTheme.colorScheme.error
            if(isSystemInDarkTheme()){
                errorColorText = MaterialTheme.colorScheme.error
                errorColorContainer = MaterialTheme.colorScheme.error
            }

            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    val listRef = firestore.collection("lists").document(listCode).get()
                        .addOnSuccessListener { document ->
                            listName.value = document.getString("listName").toString()
                            listDescription.value = document.getString("description").toString()
                            listColor.value = document.getString("color").toString()
                            loading.value = false
                        }
                }
            }
            if (loading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                AppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            topBar = {
                                SettingsAppBar(parentActivity = this@ListsSettings)
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.padding(40.dp))
                                Text(
                                    text = "Current details:",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(10.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                val newListName = OutlinedTextField(
                                    label = "List name max 14 characters",
                                    textIn = listName.value
                                )
                                val newListDescription = OutlinedTextField(
                                    label = "Description",
                                    textIn = listDescription.value
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                Text(
                                    text = "Color:",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 20.sp
                                )
                                val newListColor = DropdownMenuBox(
                                    items = colorArray,
                                    selectedItem = listColor.value
                                )

                                Spacer(modifier = Modifier.padding(10.dp))
                                ButtonFilled("Save") {
                                    if (!(newListName != listName.value || newListDescription != listDescription.value || newListColor != listColor.value)) {
                                        Toast.makeText(
                                            baseContext,
                                            "No changes",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (newListName == "") {
                                        Toast.makeText(
                                            baseContext,
                                            "List name cannot be empty",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (newListName.length > 14) {
                                        Toast.makeText(
                                            baseContext,
                                            "List name is too long",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        firestore.collection("lists").document(listCode).update(
                                            mapOf(
                                                "listName" to newListName,
                                                "description" to newListDescription,
                                                "color" to newListColor
                                            )
                                        )
                                        Toast.makeText(
                                            baseContext,
                                            "List updated",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                                Spacer(modifier = Modifier.padding(10.dp))
                                HorizontalDivider(thickness = 2.dp)
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(
                                    text = "Current code: $listCode",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(10.dp),
                                    fontSize = 20.sp
                                )

                                ButtonFilled("Reset code") {
                                    //generate a new code
                                    val firestore = Firebase.firestore
                                    val newListCode = ListOfTasks.createCode()

                                    Log.d("ListsSettings", "New code: $newListCode")

                                    //set a new code to an in collection field
                                    firestore.collection("lists").document(listCode).update(
                                        mapOf(
                                            "code" to newListCode
                                        )
                                    ).addOnSuccessListener {
                                        firestore.collection("lists").document(listCode).get()
                                            .addOnSuccessListener { document ->
                                                val data = document.data
                                                Log.d("ListsSettings", "Data: $data")
                                                //delete an old collection with an old code id
                                                firestore.collection("lists").document(listCode)
                                                    .delete()
                                                    .addOnSuccessListener {
                                                        Log.d("ListsSettings", "Document deleted")
                                                        firestore.collection("lists")
                                                            .document(newListCode).set(data!!)
                                                            .addOnSuccessListener {
                                                                Log.d(
                                                                    "ListsSettings",
                                                                    "Document added"
                                                                )
                                                                firestore.collection("lists")
                                                                    .document(newListCode).get()
                                                                    .addOnSuccessListener { document3 ->
                                                                        val data1 = document3.data
                                                                        Log.d(
                                                                            "ListsSettings",
                                                                            "Data after update: $data1"
                                                                        )

                                                                        firestore.collection("users")
                                                                            .get()
                                                                            .addOnSuccessListener { documents ->
                                                                                for (document in documents) {
                                                                                    val lists =
                                                                                        document.get(
                                                                                            "lists"
                                                                                        ) as? MutableList<String>
                                                                                    if (lists != null) {
                                                                                        if (lists.contains(
                                                                                                listCode
                                                                                            )
                                                                                        ) {
                                                                                            lists.remove(
                                                                                                listCode
                                                                                            )
                                                                                            lists.add(
                                                                                                newListCode
                                                                                            )
                                                                                            firestore.collection(
                                                                                                "users"
                                                                                            )
                                                                                                .document(
                                                                                                    document.id
                                                                                                )
                                                                                                .update(
                                                                                                    mapOf(
                                                                                                        "lists" to lists
                                                                                                    )
                                                                                                )
                                                                                        }
                                                                                    }

                                                                                }
                                                                            }
                                                                    }

                                                            }
                                                    }
                                            }
                                    }
                                    //get a data with a new code inside
                                    //Hopefully it works fine
                                    Toast.makeText(
                                        baseContext,
                                        "New code: $newListCode",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                                Spacer(modifier = Modifier.padding(10.dp))


                                Text(
                                    text = "Danger zone:",
                                    color = errorColorText,
                                    modifier = Modifier.padding(10.dp),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                HorizontalDivider(
                                    color = errorColorText,
                                    thickness = 2.dp
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                                DangerFilledButton("Delete list", color = errorColorContainer) { //TODO: EWENTUALNE BADZ MILY I ZROB DIALOG CZY NA PEWNO
                                    val db = com.google.firebase.ktx.Firebase.firestore
                                    val auth: FirebaseAuth = com.google.firebase.ktx.Firebase.auth
                                    firestore.collection("lists").document(listCode).delete()
                                        .addOnSuccessListener {
                                            firestore.collection("users").get()
                                                .addOnSuccessListener { documents ->
                                                    for (document in documents) {
                                                        val lists =
                                                            document.get("lists") as? MutableList<String>
                                                        if (lists != null) {
                                                            if (lists.contains(listCode)) {
                                                                lists.remove(listCode)
                                                                firestore.collection("users")
                                                                    .document(document.id).update(
                                                                        mapOf(
                                                                            "lists" to lists
                                                                        )
                                                                    )
                                                            }
                                                        }
                                                    }
                                                    Toast.makeText(
                                                        baseContext,
                                                        "List deleted",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                    //TODO: check if user has any lists left --------------
                                                    val currentUserR = db.collection("users")
                                                        .document(auth.currentUser?.uid!!)
                                                    currentUserR.get()
                                                        .addOnSuccessListener { documentSnapchot ->

                                                            if (documentSnapchot.exists()) {
                                                                Log.d(
                                                                    "ListsSettings",
                                                                    "DocumentSnapshot data: ${documentSnapchot.data}"
                                                                )
                                                                val lists =
                                                                    documentSnapchot.get("lists") as? MutableList<String>

                                                                if (lists.isNullOrEmpty()) {
                                                                    Log.d(
                                                                        "ListsSettings",
                                                                        "No lists go to empty lists activity"
                                                                    )
                                                                    val intent =
                                                                        Intent(
                                                                            this@ListsSettings,
                                                                            EmptyListsListActivity::class.java
                                                                        )
                                                                    startActivity(intent)
                                                                    finish()
                                                                } else {
                                                                    Log.d(
                                                                        "ListsSettings",
                                                                        "Lists exist go to lists activity"
                                                                    )
                                                                    val intent =
                                                                        Intent(
                                                                            this@ListsSettings,
                                                                            FilledListsListActivity::class.java
                                                                        )
                                                                    startActivity(intent)
                                                                    finish()
                                                                }
                                                            } else {
                                                                Log.d(
                                                                    "ListsSettings",
                                                                    "No such document"
                                                                )
                                                            }
                                                        }.addOnFailureListener { exception ->
                                                            Log.d(
                                                                "LogInActivity",
                                                                "get failed with ",
                                                                exception
                                                            )
                                                        }
                                                }
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}