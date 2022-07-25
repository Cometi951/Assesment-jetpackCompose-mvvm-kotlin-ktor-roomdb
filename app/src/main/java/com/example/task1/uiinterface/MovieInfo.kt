package com.example.moviesapp.uiinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.task1.model.InfosModel
import com.example.task1.R
import com.example.task1.viewmodel.DataViewModel

@Composable
fun MovieInfo(viewModel: DataViewModel , movieId: String, navController: NavHostController) {
    var context = LocalContext.current
    viewModel.getMovieInfo(movieId)
    var data = viewModel.MovieInfo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .height(200.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/original" + data?.backdrop_path)
                    .crossfade(500)
                    .build(),
                modifier = Modifier
                    .height(200.dp),
                contentDescription = "", contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.imageloading)
            )
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/original" + data?.poster_path)
                    .crossfade(500)
                    .build(),
                modifier = Modifier
                    .height(200.dp),
                contentDescription = "", contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.imageloading)
            )
        }

        Column(
            modifier = Modifier
                .padding(bottom = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {

                Column(modifier = Modifier.weight(80f)) {

                    Text(
                        modifier = Modifier,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = data?.original_title.toString()
                    )
                    Text(
                        modifier = Modifier,
                        fontSize = 18.sp,
                        color = Color.Black,
                        text = data?.status.toString() + " in " + data?.release_date.toString()
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth().weight(20f),
                    contentAlignment = Alignment.TopEnd
                ) {

                    var isChecked by remember { mutableStateOf(false) }

                    isChecked = viewModel.allFavMovies?.items?.any {
                        it?.id == data?.id
                    } == true

                    IconToggleButton(
                        modifier = Modifier,
                        checked = isChecked,
                        onCheckedChange = {
                            viewModel.addFavourite(movieId)
                            if (isChecked) {
                                viewModel.removeFavourite(movieId)
                            } else {
                                viewModel.addFavourite(movieId)
                            }
                        }

                    ) {
                        Icon(
                            if (isChecked) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                            contentDescription = "Favourite",
                            tint = Color.Red
                        )
//                            }
//                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )
            if (data?.production_companies != null) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        text = production_companies(data.production_companies!!)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
            }
            if (data?.genres != null) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = "Genres :"
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = genres(data?.genres),
                        color = Color.Gray,
                        fontSize = 17.sp,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
            }
            if (data?.vote_average != null) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = "Popularity :"
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
                        text = "${data.vote_average!! * 10} %",
                        fontSize = 17.sp,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
            }
            if (data?.revenue != null && data?.revenue != 0) {
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = "Revenue ($) : "
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
                        text = data?.revenue.toString(),
                        fontSize = 17.sp,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
            }

            if (data?.tagline != null && data?.tagline != "") {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = "Tagline"
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
//                            text = "text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text texttext text text text text text text text text text text text text text text text text text text text text text texttext text text text text text text text text text text text text text text text text text text text text text texttext text text text text text text text text text text text text text text text text text text text text text texttext text text text text text text text text text text text text text text text text text text text text text texttext text text text text text text text text text text text text text text text text text text text text text texttext text text text text text text text text text text text text text text text text text text text text text texttext text text text text text text text text text text text text text text text text text text text text text text",
                        text = data?.tagline.toString(),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Justify
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
            }

            if (data?.overview != null) {
                Column(
                    modifier = Modifier
//                                .shadow(1.dp)
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = "Overview"
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray,
                        text = data?.overview.toString(),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}

fun production_companies(productionCompanies: List<InfosModel.ProductionCompany?>): String {
    var str = StringBuilder("")
    if (productionCompanies != null) {
        for (i in productionCompanies) {
            str.append(i?.name.toString())
            if (i != productionCompanies.lastOrNull())
                str.append(",")
        }
    }
    return str.toString()
}


fun genres(genres: List<InfosModel.Genre?>?): String {
    var str = StringBuilder("")
    if (genres != null) {
        for (i in genres) {
            str.append(i?.name.toString())
            if (i != genres.lastOrNull())
                str.append(",")
        }
    }
    return str.toString()
}

