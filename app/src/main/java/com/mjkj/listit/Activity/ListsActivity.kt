package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
class ListsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val items = listOf(
                NavigationItem(
                    title = "All",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Filled.Home,
                ),
                NavigationItem(
                    title = "Urgent",
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Filled.Info,
                    badgeCount = 45
                ),
                NavigationItem(
                    title = "Settings",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Filled.Settings,
                ),
            )
            var showDialog by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            Spacer(modifier = Modifier.height(16.dp))
                            items.forEachIndexed { index, item ->
                                NavigationDrawerItem(
                                    label = {
                                        Text(text = item.title)
                                    },
                                    selected = index == 0,
                                    onClick = {
                                        showDialog = false
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == 0) {
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
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                        }
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            ListAppBar(
                                activity = "MainActivity",
                                onMenuClicked = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                },
                                onAddClicked = {
                                    showDialog = true
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Press the + to join a list or to create a new one \n",
                                textAlign = TextAlign.Center,
                                color = Color.Gray,
                                fontSize = 25.sp,
                                modifier = Modifier.padding(15.dp) // Padding dla zawartości
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ListAppBar(
    activity: String,
    onMenuClicked: () -> Unit,
    onAddClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onMenuClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            Text(
                text = "List-it",
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp
            )
            IconButton(onClick = { onAddClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}
