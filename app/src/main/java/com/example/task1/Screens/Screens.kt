package com.example.task1.Screens

sealed class Screens (val route: String){
    object Main: Screens("main")
    object Edit: Screens("edit")
    object HomeScreen : Screens("HomeScreen")
    object FavouriteMovies : Screens("FavouriteMovies")
    object MovieInfo : Screens("MovieInfo")
}