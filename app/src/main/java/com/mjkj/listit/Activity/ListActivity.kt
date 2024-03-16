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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjkj.listit.Composable.ListAppBar

@Suppress("IMPLICIT_CAST_TO_ANY")
class ListActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listCode: String = intent.getStringExtra("listCode").toString()
        val listTitle: String = intent.getStringExtra("listTitle").toString()
        val listColor: String = intent.getStringExtra("listColor").toString()
        setContent {
            val listOfLists = mutableStateListOf<MutableList<String>>()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background)
            {
                Scaffold(
                    topBar = {
                        ListAppBar(
                            activity = "ListActivity",
                            this,
                            listOfLists,
                            listTitle,
                            listColor,
                        )
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = listCode,
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