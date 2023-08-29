package com.example.project.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.project.data.helper.fetchDataPopular
import com.example.project.data.helper.fetchDataTopRated
import com.example.project.data.model.MovieResponse
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.launch

@Composable
fun Home(navController: NavController){
    val responseTopRated = remember { mutableStateOf<MovieResponse?>(null) }
    val responsePopular = remember { mutableStateOf<MovieResponse?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MjAwYjZhNmQxYzdjZDQzNDcyZjNiMTg3ZTE0YWQ3NiIsInN1YiI6IjY0ZTc1ZDk0NTI1OGFlMDE0ZGYxYjg4NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P_iV_ouxPdN7VLOAfek_550t_YLS6WwylWypbyGBni0"
        coroutineScope.launch {
            val data = fetchDataTopRated(token)
            responseTopRated.value = data
        }
    }

    LaunchedEffect(key1 = true) {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MjAwYjZhNmQxYzdjZDQzNDcyZjNiMTg3ZTE0YWQ3NiIsInN1YiI6IjY0ZTc1ZDk0NTI1OGFlMDE0ZGYxYjg4NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P_iV_ouxPdN7VLOAfek_550t_YLS6WwylWypbyGBni0"
        coroutineScope.launch {
            val data = fetchDataPopular(token)
            responsePopular.value = data
        }
    }

    val resultTopRated = responseTopRated.value

    val resultPopular = responsePopular.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ){
        Column {
            Text(
                text = "Top Rated",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, top = 16.dp),
                fontSize = 30.sp,
                color = Color.White
            )

            LazyRow(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                contentPadding = PaddingValues(end = 16.dp)
            ) {
                items(resultTopRated?.results ?: emptyList()) { movie ->
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
                                .clip(RoundedCornerShape(8.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Popular",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, top = 16.dp),
                fontSize = 30.sp,
                color = Color.White
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                content = {
                    items(resultPopular?.results ?: emptyList()) { movie ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("Detail/${movie.id}")
                                    },
                                contentAlignment = Alignment.Center
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
                                        .clip(RoundedCornerShape(8.dp))
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
            )
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