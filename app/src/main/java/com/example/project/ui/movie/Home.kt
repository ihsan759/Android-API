package com.example.project.ui.movie

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.project.data.configuration.RetrofitClient
import com.example.project.data.helper.fetchDataNowPlaying
import com.example.project.data.model.MovieResponse
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Home(navController: NavController){
    val response = remember { mutableStateOf<MovieResponse?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MjAwYjZhNmQxYzdjZDQzNDcyZjNiMTg3ZTE0YWQ3NiIsInN1YiI6IjY0ZTc1ZDk0NTI1OGFlMDE0ZGYxYjg4NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P_iV_ouxPdN7VLOAfek_550t_YLS6WwylWypbyGBni0"
        coroutineScope.launch {
            val data = fetchDataNowPlaying(token)
            response.value = data
        }
    }

    val result = response.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ){
        Column (
            modifier = Modifier
                .background(color = Color.White)
        ) {
            Text(
                text = "Top Rated",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, top = 16.dp),
                fontSize = 20.sp
            )

            LazyRow(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                contentPadding = PaddingValues(end = 16.dp)
            ) {
                items(result?.results ?: emptyList()) { movie ->
                    // Movie content here
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(150.dp) // Set the same height for each item
                            .padding(end = 16.dp)
                            .clickable {
                                navController.navigate("Detail/${movie.id}")
                            },
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = "https://image.tmdb.org/t/p/original"+movie.backdrop_path,
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = movie.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ProjectTheme {
        val navController = rememberNavController()
        Home(navController = navController)
    }
}