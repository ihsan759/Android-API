package com.example.project.ui.user

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.R
import com.example.project.data.database.AppDatabase
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun Login(navController: NavController, database: AppDatabase, context: Context) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var isUsernameValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }

    val userDao = database.userDao()
    var showError by remember { mutableStateOf(false) }

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White)) {
            append("Don't have an account? ")
        }
        withStyle(style = SpanStyle(color = Color.Blue)) {
            append("Register")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Login",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 60.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (showError) {
                Text(
                    text = "Invalid username or password",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            TextField(
                value = username,
                onValueChange = { newText ->
                    username = newText
                    isUsernameValid = newText.isNotBlank()
                },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp)
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
                onValueChange = { newText ->
                    password = newText
                    isPasswordValid = newText.isNotBlank()
                },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp)
            )
            if (!isPasswordValid && password.isEmpty()) {
                Text(
                    text = "Password is required",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Button(
                modifier = Modifier
                    .width(200.dp)
                    .padding(16.dp),
                onClick = {
                    isUsernameValid = username.isNotBlank()
                    isPasswordValid = password.isNotBlank()
                    if (isUsernameValid && isPasswordValid) {
                      val user = runBlocking {
                            userDao.getUserByUsernameAndPassword(username, password)
                      }
                        if (user != null){
                            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            sharedPreferences.edit {
                                putInt("id", user.id)
                                putBoolean("isLogged", true)
                            }
                            navController.navigate("Home")
                        }else{
                            showError = true
                        }
                    }
                }
            ) {
                Text("Login")
            }

            ClickableText(
                text = text,
                onClick = { offset ->
                    if (offset >= text.indexOf("Register") && offset < text.indexOf("Register") + "Register".length) {
                        navController.navigate("Register")
                    }
                }
            )
        }
    }

}