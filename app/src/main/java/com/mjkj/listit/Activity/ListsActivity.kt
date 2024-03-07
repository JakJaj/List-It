package com.mjkj.listit.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
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
import com.mjkj.listit.Composable.ButtonTonalFilled
import com.mjkj.listit.Composable.ListAppBar
import kotlinx.coroutines.launch
import com.mjkj.listit.Composable.*

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

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
                        NavDrawer(
                            items = items,
                            showDialog = showDialog,
                            onShowDialogChange = { showDialog = it }
                        )
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            ListAppBar(
                                activity = "ListActivity",
                                onMenuClicked = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                },
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
                                modifier = Modifier.padding(15.dp)
                            )
                        }
                    }
                }
            }

        }
    }
}

