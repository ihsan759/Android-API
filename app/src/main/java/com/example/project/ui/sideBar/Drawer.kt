package com.example.project.ui.sideBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.ui.dashboard.Home
import com.example.project.ui.theme.ProjectTheme
import com.example.project.ui.user.Login
import com.example.project.ui.user.Register
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(navController: NavController) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val isDrawerVisible = remember { mutableStateOf(false) }
    ModalNavigationDrawer(drawerContent = {
                                        if (isDrawerVisible.value){
                                            DrawerContent(
                                                navController = navController,
                                                drawerState = drawerState
                                            )
                                        }
    }, drawerState = drawerState,
        scrimColor = Color.DarkGray
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Nobar", color = Color.White, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Blue
                    ),
                    navigationIcon = {
                        if (isDrawerVisible.value){
                            IconButton(onClick = {
                                if (drawerState.isClosed){
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }else{
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    }
                )
            }){
            Box(modifier = Modifier.padding(it)){
                NavHost(navController = navController, startDestination = "Home") {
                    composable("Login") {
                        isDrawerVisible.value = false
                        Login(navController = navController)
                    }
                    composable(route = "Home") {
                        isDrawerVisible.value = true
                        Home(navController = navController)
                    }
                    composable("Register") {
                        isDrawerVisible.value = false
                        Register(navController = navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerPreview() {
    ProjectTheme {
        val navController = rememberNavController()
        Drawer(navController = navController)
    }
}
