package com.mjkj.listit.Composable

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.mjkj.listit.Activity.ListActivity
import com.mjkj.listit.Activity.MainActivity


@Composable
fun NavDrawer(parentActivity: Activity, listOfLists: List<List<String>>) {
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
                        code = listOfLists[i][3]
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
                    //TODO: Implement settings
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonTonalFilled(label = "Log out") {
                    //TODO: Implement log out
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
fun NavDrawerItem(title: String, description: String, color: String, context: Context, code:String) {
    val backgroundColor = Color.Transparent
    val circleColor = remember {
        when (color) {
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
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                val intent = Intent(context, ListActivity::class.java)
                intent.putExtra("listCode", code)
                context.startActivity(intent)
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
                    color = Color.Black,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    color = Color.Black,
                    fontSize = 10.sp
                )
            }
        }
    }
}