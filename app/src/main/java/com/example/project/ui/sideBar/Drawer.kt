package com.example.project.ui.sideBar

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.data.database.AppDatabase
import com.example.project.data.model.WhiteList
import com.example.project.ui.movie.BookMark
import com.example.project.ui.movie.Detail
import com.example.project.ui.movie.Home
import com.example.project.ui.user.Login
import com.example.project.ui.user.Register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(database: AppDatabase) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val isDrawerVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val detail = remember { mutableStateOf(false) }
    val isBookmark = remember { mutableStateOf(false) }
    val id = remember { mutableStateOf(0) }
    val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    ModalNavigationDrawer(drawerContent = {
                                        if (isDrawerVisible.value){
                                            DrawerContent(
                                                navController = navController,
                                                drawerState = drawerState,
                                                context = context
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
                        }else{
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        if (id.value != 0 && sharedPrefs.getBoolean("isLogged",false)){
                            val whiteListDao = database.whiteListDao()
                            LaunchedEffect(Unit) {
                                // Get bookmark status based on current id and user
                                val isBookmarked = withContext(Dispatchers.IO) {
                                    whiteListDao.get(id.value, sharedPrefs.getInt("id", 0)) != null
                                }
                                isBookmark.value = isBookmarked
                            }

                            IconButton(
                                onClick = {

                                    val cek = runBlocking {
                                        whiteListDao.get(id.value, sharedPrefs.getInt("id",0))
                                    }
                                    if (cek != null){
                                        runBlocking {
                                            whiteListDao.delete(cek.id)
                                        }
                                        isBookmark.value = false
                                    }else{
                                        val newWhieList = WhiteList(idFilm = id.value, idUser = sharedPrefs.getInt("id",0))
                                        runBlocking {
                                            whiteListDao.insert(newWhieList)
                                        }

                                        isBookmark.value = true
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if(isBookmark.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color.Yellow
                                )
                            }
                        }
                    }
                )
            }){
            Box(modifier = Modifier.padding(it)){
                NavHost(navController = navController, startDestination = "Home") {
                    composable("Login") {
                        isDrawerVisible.value = false
                        id.value = 0
                        Login(navController = navController, database, context)
                    }
                    composable(route = "Home") {
                        isDrawerVisible.value = true
                        id.value = 0
                        Home(navController = navController)
                    }
                    composable(route = "Bookmark"){
                        isDrawerVisible.value = true
                        id.value = 0
                        BookMark(navController = navController, database = database, context = context)
                    }
                    composable("Register") {
                        isDrawerVisible.value = false
                        id.value = 0
                        Register(navController = navController, database)
                    }
                    composable(route = "Detail/{id}") {backStackEntry ->
                        isDrawerVisible.value = false
                        detail.value = true
                        var data = backStackEntry.arguments?.getString("id") ?:""
                        id.value = data.toInt()
                        Detail(id = id.value)
                    }
                }
            }
        }
    }
}
