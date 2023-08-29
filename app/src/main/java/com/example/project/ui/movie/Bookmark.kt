package com.example.project.ui.movie

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.project.data.database.AppDatabase
import com.example.project.data.helper.fetchDataPopular
import com.example.project.data.helper.fetchDataTopRated
import com.example.project.data.model.Movie

@Composable
fun BookMark(navController: NavController, database: AppDatabase, context: Context){
        val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val responseMovies = remember { mutableStateOf<List<Movie>?>(null) }
    LaunchedEffect(key1 = true) {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MjAwYjZhNmQxYzdjZDQzNDcyZjNiMTg3ZTE0YWQ3NiIsInN1YiI6IjY0ZTc1ZDk0NTI1OGFlMDE0ZGYxYjg4NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P_iV_ouxPdN7VLOAfek_550t_YLS6WwylWypbyGBni0"

        // Fetch popular movies from the API
        val popularMovies = fetchDataPopular(token)
        val topRatedMovies = fetchDataTopRated(token)

        val combinedMovies = popularMovies.results.toMutableList().apply { addAll(topRatedMovies.results) }

        // Get the user ID from SharedPreferences
        val userId = sharedPrefs.getInt("id", 0)

        // Get the user's white list from Room
        val whiteList = database.whiteListDao().getAll(userId)

        val idFilm = whiteList?.map { it.idFilm }

        val filteredMovies = combinedMovies.filter { movie ->
            idFilm?.contains(movie.id) ?: false
        }

        responseMovies.value = filteredMovies


    }

    val data = responseMovies.value



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ){
        LazyColumn {
            items(data ?: emptyList()) { movie ->
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(start = 16.dp, end = 16.dp)
                        .clickable {
                            navController.navigate("Detail/${movie.id}")
                        }
                        .background(color = Color.White)
                        .clip(RoundedCornerShape(8.dp))
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = "https://image.tmdb.org/t/p/original/"+movie.backdrop_path,
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = movie.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (movie.overview.length > 80) {"${movie.overview.take(80)}..."} else {movie.overview},
                                fontSize = 10.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}