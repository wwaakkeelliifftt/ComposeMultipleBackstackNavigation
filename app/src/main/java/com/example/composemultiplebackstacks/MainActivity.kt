package com.example.composemultiplebackstacks

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composemultiplebackstacks.ui.theme.ComposeMultipleBackstacksTheme

const val HOME = "home"
const val CHAT = "chat"
const val SETTINGS = "settings"

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMultipleBackstacksTheme {

                val rootNavController = rememberNavController()
                val navBackStackEntry by rootNavController.currentBackStackEntryAsState()


                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEach { item ->
                                val isSelected =
                                    item.title == navBackStackEntry?.destination?.route

                                NavigationBarItem(
                                    selected = isSelected,
                                    label = { Text(text = item.title) },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            contentDescription = null
                                        ) },
                                    onClick = {
                                        rootNavController.navigate(item.title.lowercase()) {
                                            popUpTo(rootNavController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = rootNavController,
                        startDestination = HOME
                    ) {
                        composable(route = HOME) {
                            HomeNavHost()
                        }
                        composable(route = CHAT) {
                            ChatNavHost()
                        }
                        composable(route = SETTINGS) {
                            SettingsNavHost()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeNavHost() {
    val homeNavController = rememberNavController()
    NavHost(
        navController = homeNavController,
        startDestination = "home1"
    ) {
        for(i in 1..10) {
            val route = "home$i"
            composable(route) {
                GenericScreen(text = "Home $i") {
                    if (i < 10) {
                        homeNavController.navigate("home${i + 1}")
                    }
                }
            }
        }
    }
}

@Composable
fun ChatNavHost() {
    val chatNavController = rememberNavController()
    NavHost(
        navController = chatNavController,
        startDestination = "chat1"
    ) {
        for(i in 1..10) {
            val route = "chat$i"
            composable(route) {
                GenericScreen(text = "Chat $i") {
                    if (i < 10) {
                        chatNavController.navigate("chat${i + 1}")
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsNavHost() {
    val settingsNavController = rememberNavController()
    NavHost(
        navController = settingsNavController,
        startDestination = "settings1"
    ) {
        for(i in 1..10) {
            val route = "settings$i"
            composable(route) {
                GenericScreen(text = "Settings $i") {
                    if (i < 10) {
                        settingsNavController.navigate("settings${i + 1}")
                    }
                }
            }
        }
    }
}

@Composable
fun GenericScreen(text: String, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNextClick) {
            Text(text = "Next")
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val items = listOf(
    BottomNavigationItem(
        title = HOME,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        title = CHAT,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email
    ),
    BottomNavigationItem(
        title = SETTINGS,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
)



