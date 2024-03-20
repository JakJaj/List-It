package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mjkj.listit.Composable.ListAppBar
import com.mjkj.listit.Composable.ListItem

class FilledTasksTaskActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val listOfCodes = mutableListOf<String>()
            val listOfTasks = mutableStateListOf<MutableList<String>>()
            val listCode: String = intent.getStringExtra("listCode").toString()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    topBar = {
                        ListAppBar(
                            activity = "FilledTasksTaskActivity",
                            this,
                            listOfTasks,
                            listCode,
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
                                ListItem(
                                    title = listOfTasks[i][0],
                                    description = listOfTasks[i][1],
                                    color = listOfTasks[i][2],
                                    context = this@FilledTasksTaskActivity,
                                    code = listOfTasks[i][3],
                                    listOfTasks
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
