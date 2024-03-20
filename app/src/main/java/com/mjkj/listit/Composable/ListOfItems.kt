package com.mjkj.listit.Composable

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjkj.listit.Activity.EmptyTasksTaskActivity
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
            .padding(5.dp)
            .clickable {
                val intent = Intent(context, EmptyTasksTaskActivity::class.java)
                intent.putExtra("listCode", code)
                intent.putExtra("listColor", color)
                intent.putExtra("listTitle", title)
                intent.putExtra("navDrawerList", ListItemData(navDrawerList))

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
                fontSize = 15.sp
            )
        }
    }
}

