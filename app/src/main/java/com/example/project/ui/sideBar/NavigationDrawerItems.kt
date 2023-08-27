package com.example.project.ui.sideBar

import android.content.Context
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerItems(navController: NavController, drawerState: DrawerState, context: Context){
    var scope = rememberCoroutineScope()

    val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

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
    if (sharedPrefs.getBoolean("isLogged",false)){
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "BookMark") },
            label = { Text(text = "BookMark") },
            selected = destionation?.route == "BookMark",
            onClick = {
                navController.navigate("BookMark", navOptions {
                    this.launchSingleTop = true
                    this.restoreState = true
                })

                scope.launch {
                    drawerState.close()
                }
            }, modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout") },
            label = { Text(text = "Logout") },
            selected = destionation?.route == "Login",
            onClick = {
                sharedPrefs.edit {
                    remove("id")
                    putBoolean("isLogged",false)
                }
                navController.navigate("Login", navOptions {
                    this.launchSingleTop = true
                    this.restoreState = true
                })

                scope.launch {
                    drawerState.close()
                }
            }, modifier = Modifier.padding(horizontal = 16.dp)
        )
    }else{
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Login") },
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
}