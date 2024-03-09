package com.mjkj.listit.Composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
        /** This is a composable function that creates a button WITH a filled background and a label
         *  this button is used as a main action button
         *
         * @param label: String - The text to be displayed on the button
         * @param onClick: () -> Unit - The action to be performed when the button is clicked
         * */
fun ButtonFilled(label: String,onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Composable
        /** This is a composable function that creates a button WITHOUT a filled background and a with a passed label
         *  this button is used as a secondary action button
         *
         * @param label: String - The text to be displayed on the button
         * @param onClick: () -> Unit - The action to be performed when the button is clicked
         **/
fun ButtonTonalFilled(label:String,onClick: () -> Unit) {
    FilledTonalButton(onClick = { onClick() }) {
        Text(label,fontSize = 25.sp)
    }
}

@Composable
        /** This is a composable function that creates a text field with an outlined border
         *
         *  @param label: String - The text to be displayed on the label
         * */
fun OutlinedTextField(label:String) {
    var text by remember { mutableStateOf("") }

    androidx.compose.material3.OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) }
    )
}

@Composable
        /**
         * Composable function to display an app bar for a list screen.
         *
         * @param activity The title of the activity associated with the app bar.
         */
fun ListAppBar(
    activity: String,
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showNavDrawer = remember {
        mutableStateOf(false)
    }

    if (activity == "ListActivity" && showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false })
    }

    if (activity == "ListActivity" && showNavDrawer.value) {
        NavDrawer(onDismissDrawerRequest = { showNavDrawer.value = false })
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showNavDrawer.value = true }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            Text(
                text = "List-it",
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp
            )
            IconButton(onClick = { showDialog.value = true }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Surface (modifier = Modifier.fillMaxSize()){
        Text(text = "List It",fontSize = 120.sp)
        Dialog {
        }
    }
}

@Composable
        /** This is a composable function that creates a dialog box
         *
         * @param onDismissRequest: () -> Unit - The action to be performed when the dialog is dismissed
         * */
fun Dialog(onDismissRequest: () -> Unit) {
    var showCreateListContent by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(650.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                TextButton(onClick = {showCreateListContent = false}){
                    Text(text = "Join List")
                }
                TextButton(onClick = {showCreateListContent = true}) {
                    Text(text = "Create List")
                }
            }
            HorizontalDivider()
            if(showCreateListContent){
                CreateListContent()
            }else{
                JoinListContent()
            }

        }
        }
    }
}

@Composable
        /** This is a composable function that joins to an existing a new list
         *
         * */
fun JoinListContent(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Join an existing list",fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(25.dp))
        OutlinedTextField("List Code")
        Spacer(modifier = Modifier.padding(20.dp))


        Text(text = "Enter the code of the list you want to join",fontSize = 14.sp)
        Spacer(modifier = Modifier.padding(20.dp))
        HorizontalDivider(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.padding(75.dp))
        ButtonFilled("Join") {

        }
    }
}

@Composable
    /** This is a composable function that creates submenu for creating a new list
     *
     * */
fun CreateListContent(){
    val colorArray = arrayOf("Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Pink", "Brown", "Black", "White")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Create a new list",fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(20.dp))
        OutlinedTextField("List Name (Required)")

        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField("Short Description ")
        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = "Choose a color for your list",fontSize = 14.sp)
        DropdownMenuBox(colorArray)
        Spacer(modifier = Modifier.padding(15.dp))
        HorizontalDivider(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        ButtonFilled("Create") {

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
        /** This is a composable function that creates a dropdown menu
        * @param items: Array<String> - The items to be displayed in the dropdown menu
        * */
fun DropdownMenuBox(items: Array<String>) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(items[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)
@Composable
fun NavDrawer(onDismissDrawerRequest: () -> Unit) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet( onDismissDrawerRequest = { onDismissDrawerRequest() }) {
        val items = remember {
            listOf(
                NavigationItem(
                    title = "All",
                    selectedIcon = Icons.Default.Home,
                    unselectedIcon = Icons.Default.Home,
                ),
                NavigationItem(
                    title = "Urgent",
                    selectedIcon = Icons.Default.Info,
                    unselectedIcon = Icons.Default.Info,
                    badgeCount = 45
                ),
                NavigationItem(
                    title = "Settings",
                    selectedIcon = Icons.Default.Settings,
                    unselectedIcon = Icons.Default.Settings,
                ),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top section with the text "List it"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "List it",
                    fontSize = 30.sp
                )
            }
            // Middle section with ListView
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(items) { item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = items.indexOf(item) == 0,
                        onClick = {
                        },
                        icon = {
                            Icon(
                                imageVector = if (items.indexOf(item) == 0) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
            // Bottom section with the text "Log out"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonTonalFilled(label = "Log out") {
                }
            }
        }
    }
}