package com.example.project.ui.user

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.project.R
import com.example.project.ui.AppViewModelProvider
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: RegisterViewModel = viewModel()

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
                value = viewModel.userUiState.userDetail.name,
                onValueChange = {
                    viewModel.userUiState.userDetail = viewModel.userUiState.userDetail.copy(name = it)
                },
                label = { Text("FullName") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            TextField(
                value = viewModel.userUiState.userDetail.username,
                onValueChange = {
                    viewModel.userUiState.userDetail = viewModel.userUiState.userDetail.copy(username = it)
                },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            TextField(
                value = viewModel.userUiState.userDetail.password,
                onValueChange = {
                    viewModel.userUiState.userDetail = viewModel.userUiState.userDetail.copy(password = it)
                },
                label = { Text("Password") },
                visualTransformation = if (viewModel.userUiState.userDetail.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Text(
                text = "Required",
                modifier = Modifier.padding(8.dp)
            )

            Button(
                onClick = {
                    if (viewModel.validateInput()) {
                        coroutineScope.launch {
                            viewModel.saveItem()
                            navController.navigate("list")
                        }
                    }
                },
                enabled = viewModel.userUiState.isEntryValid,
                modifier = Modifier
                    .width(200.dp)
                    .padding(16.dp)
            ) {
                Text("Register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    ProjectTheme {
        val navController = rememberNavController()
        Register(navController = navController)
    }
}