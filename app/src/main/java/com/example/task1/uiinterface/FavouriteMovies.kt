package com.example.moviesapp.uiinterface

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.task1.R
import com.example.task1.Screens.Screens
import com.example.task1.viewmodel.DataViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteMovies(viewModel: DataViewModel, navController: NavHostController) {
    viewModel.getFavMovies()


    var data = viewModel.allFavMovies

    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var view by remember { mutableStateOf(false) }
        var viewtype by remember { mutableStateOf(R.drawable.ic_baseline_apps_24) }

        viewtype = if (!view) R.drawable.ic_baseline_view_list_24
        else R.drawable.ic_baseline_apps_24

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(60.dp)
                .shadow(1.dp, RectangleShape)
                .padding(vertical = 10.dp, horizontal = 25.dp)
        ) {

            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
            Text(
                text = " Favourite Movies",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 20.sp
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(end = 10.dp), onClick = {
                        view = !view
                    }) {
                    Icon(
                        painter = painterResource(id = viewtype),
                        contentDescription = ""
                    )
                }
            }
        }

        val row = if (view) 1
        else 2

        val height = if (!view) 250.dp
        else 450.dp

        LazyVerticalGrid(
            modifier = Modifier.padding(20.dp),
            cells = GridCells.Fixed(row)
        ) {
            if (data != null) {
                items(data.item_count ?: 0) { i ->

                    val item = data.items!![i]

                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier
                            .padding(5.dp)
                            .height(height)
                            .clip(RoundedCornerShape(15.dp))
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screens.MovieInfo.route + "/${item?.id.toString()}")
                            }
                    )
                    {
                        AsyncImage(
                            model = ImageRequest.Builder(context = context)
                                .data("https://image.tmdb.org/t/p/original" + item?.poster_path)
                                .crossfade(500)
                                .build(),
                            modifier = Modifier
                                .fillMaxSize(),
                            contentDescription = "", contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.imageloading)
                        )
                        Text(
                            text = "",
                            Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.Black
                                        )
                                    )
                                )
                        )
                        Text(
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 40.dp, bottom = 10.dp)
                                .fillMaxWidth(),
                            text = item?.original_title.toString(),
                            color = Color.White
                        )
                        Box(modifier = Modifier, contentAlignment = Alignment.TopEnd){
                            IconButton(
                                modifier = Modifier,
                                onClick = { viewModel.removeFavourite(item?.id.toString()) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            } else {
                item {
                    Box(modifier = Modifier) {
                        Text(text = "Somthing went wrong")
                    }
                }
            }
        }
    }
}