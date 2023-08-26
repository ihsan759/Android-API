package com.example.project.ui.sideBar

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerItems(navController: NavController, drawerState: DrawerState){
    var scope = rememberCoroutineScope()

    var currentBackStackEntryAsState = navController.currentBackStackEntryAsState()

    var destionation = currentBackStackEntryAsState.value?.destination

    NavigationDrawerItem(
        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
        label = { Text(text = "Home") },
        selected = destionation?.route == "Home",
        onClick = {
            navController.navigate("Home", navOptions {
                this.launchSingleTop = true
                this.restoreState = true
            })

            scope.launch {
                drawerState.close()
            }
        }, modifier = Modifier.padding(16.dp)
    )
    NavigationDrawerItem(
        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Login") },
        label = { Text(text = "Login") },
        selected = destionation?.route == "Login",
        onClick = {
            navController.navigate("Login", navOptions {
                this.launchSingleTop = true
                this.restoreState = true
            })

            scope.launch {
                drawerState.close()
            }
        }, modifier = Modifier.padding(horizontal = 16.dp)
    )
}