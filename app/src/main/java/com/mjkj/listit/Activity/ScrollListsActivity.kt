package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjkj.listit.Composable.ListAppBar

class ScrollListsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    topBar = {
                        ListAppBar(
                            activity = "ListActivity",
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
                            for (i in 1..10) {
                                item {
                                    ListItem(
                                        title = "Example ${i}",
                                        description = "Example${i}",
                                        color = ""
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

@Composable
fun ListItem(title: String, description: String, color: String) {
    val backgroundColor = when (color.lowercase()) {
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
