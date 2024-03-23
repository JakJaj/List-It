package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mjkj.listit.Composable.ButtonFilled
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.DangerFilledButton
import com.mjkj.listit.Composable.DropdownMenuBox
import com.mjkj.listit.Composable.ListAppBar
import com.mjkj.listit.Composable.OutlinedTextField

class ListsSettings : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Surface (modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Top,
                ){
                    Text(
                        text = "Settings",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(10.dp)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    ButtonFilled("Change password") {
                        //TODO
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    ButtonTonalFilled("Log out") {
                        //TODO
                    }
                }

            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun ListsPreview() {
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

    Surface (modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Scaffold(
            topBar = {
                ListAppBar(
                    activity = "FilledListsListActivity",
                    Activity(),
                    listOfLists = listOf(listOf()),
                    null,
                )
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
                OutlinedTextField(label = "List name", textIn = "Test")
                OutlinedTextField(label = "Description", textIn = "Test")
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "Color:", color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
                DropdownMenuBox(items = colorArray, selectedItem = "")

                Spacer(modifier = Modifier.padding(10.dp))
                ButtonFilled("Save") {
                    //TODO
                }
                Spacer(modifier = Modifier.padding(20.dp))
                HorizontalDivider(thickness = 2.dp)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Current code:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp
                )

                ButtonFilled("Reset code") {
                    //TODO
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Danger zone:",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.error, thickness = 2.dp)
                Spacer(modifier = Modifier.padding(20.dp))
                DangerFilledButton("Delete list") {
                    //TODO
                }
            }
        }
    }
}
