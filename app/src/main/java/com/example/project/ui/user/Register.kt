package com.example.project.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.project.R
import com.example.project.data.database.AppDatabase
import com.example.project.data.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavController, database: AppDatabase) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    var isUsernameValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isNameValid by remember { mutableStateOf(true) }

    val userDao = database.userDao()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 45.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text("FullName") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            if (!isNameValid && name.isEmpty()) {
                Text(
                    text = "Name is required",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            if (!isUsernameValid && username.isEmpty()) {
                Text(
                    text = "Username is required",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            if (!isPasswordValid && password.isEmpty()) {
                Text(
                    text = "Password is required",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Button(
                onClick = {
                    isUsernameValid = username.isNotBlank()
                    isPasswordValid = password.isNotBlank()
                    isNameValid = name.isNotBlank()
                    if (isUsernameValid && isPasswordValid && isNameValid) {
                        val newUser = User(name = name, username = username, password = password)
                        runBlocking {
                            userDao.insert(newUser)
                        }
                        navController.navigate("Login")
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(16.dp)
            ) {
                Text("Register")
            }
        }
    }

}