package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjkj.listit.Composable.ListAppBar
import com.mjkj.listit.Composable.ListItemData
import com.mjkj.listit.ui.theme.AppSettings
import com.mjkj.listit.ui.theme.AppTheme

@Suppress("DEPRECATION")
class EmptyTasksTaskActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppSettings.init(applicationContext)
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
            android.util.Log.d("LogInActivity", "List Color: $listColor")
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    Scaffold(
                        topBar = {
                            ListAppBar(
                                activity = "EmptyTasksTaskActivity",
                                this,
                                listOfLists,
                                listCode,
                                listTitle,
                                listColor
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Press the + to create a new task",
                                textAlign = TextAlign.Center,
                                color = Color.Gray,
                                fontSize = 25.sp,
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}