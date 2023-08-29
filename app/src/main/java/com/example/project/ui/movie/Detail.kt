package com.example.project.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.project.data.database.AppDatabase
import com.example.project.data.helper.ClickableLinkText
import com.example.project.data.helper.fetchDetail
import com.example.project.data.model.MovieDetailResponse
import kotlinx.coroutines.launch

@Composable
fun Detail(id: Int) {

    val response = remember { mutableStateOf<MovieDetailResponse?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MjAwYjZhNmQxYzdjZDQzNDcyZjNiMTg3ZTE0YWQ3NiIsInN1YiI6IjY0ZTc1ZDk0NTI1OGFlMDE0ZGYxYjg4NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P_iV_ouxPdN7VLOAfek_550t_YLS6WwylWypbyGBni0"
        coroutineScope.launch {
            val data = fetchDetail(token, id)
            response.value = data
        }
    }

    val result = response.value

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .padding(16.dp)
        ){
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/original${result?.poster_path}",
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "${result?.title}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "${result?.overview}")
            Spacer(modifier = Modifier.height(16.dp))
            if (result?.homepage != ""){
                ClickableLinkText(uri = result?.homepage ?: "", hyperlinkText = "Click Here To HomePage")
            }
        }
    }
}

