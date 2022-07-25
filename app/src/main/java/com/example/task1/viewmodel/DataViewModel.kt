package com.example.task1.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shyama.android.database.ApiService
import com.example.task1.database.Task1DB
import com.example.task1.model.*
import com.example.task1.repository.Repository
import com.example.task1.stateData.StateData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(applicationContext: Context) : ViewModel() {

    var roomdb = Task1DB.getinstance(applicationContext)
    var dao = roomdb.dao


    var dbData: List<Item>? by mutableStateOf(listOf(Item()))
        private set

    var allMovies: MoviesModel? by mutableStateOf(MoviesModel())
        private set

    fun getAllMovies() {
        viewModelScope.launch {
            dbData = dao.getMovie()
            allMovies = ApiService.getTopMovies()

            if (allMovies?.results.isNullOrEmpty()) allMovies = MoviesModel(results = dbData)
            else {
                var data = allMovies?.results?.filterNot { it1 ->
                    dbData!!.any { it2 ->
                        it1.id == it2.id
                    }
                }
                if (data != null) {
                    dao.insertAll(data)
                }
            }
        }
    }

    var allFavMovies: FavMoviesModel? by mutableStateOf(FavMoviesModel())
        private set

    fun getFavMovies() {
        viewModelScope.launch {
            var dbData = dao.getFavMovies()
            allFavMovies = ApiService.getFavourite()
            if (allFavMovies?.items.isNullOrEmpty()) allFavMovies = FavMoviesModel(items = dbData)
            else {
                var data = allFavMovies?.items?.filterNot { it1 ->
                    dbData!!.any { it2 ->
                        it1?.id == it2.id
                    }
                }
                if (data != null) {
                    dao.deleteFav()
                    dao.insertFav(data as List<fav_movie>)
                }
            }
        }
    }

    var MovieInfo: InfosModel? by mutableStateOf(InfosModel())
        private set

    fun getMovieInfo(movieId: String) {
        getFavMovies()
        viewModelScope.launch {
            MovieInfo = ApiService.getMovieInfo(movieId)
        }
    }

    fun addFavourite(movieId: String) {
        viewModelScope.launch {
            ApiService.addFavourite(movieId)
            getFavMovies()
        }
    }

    fun removeFavourite(movieId: String) {
        viewModelScope.launch {
            ApiService.removeFavourite(movieId)
            getFavMovies()
        }
    }


    val repository by lazy {
        Repository()
    }


    fun insertData(context: Context, data: ArrayList<User>) {
        viewModelScope.launch {
            repository.insertData(context, data)
        }
    }


    //private val data: MutableLiveData<List<User>> = MutableLiveData()
    val getData = MutableStateFlow<StateData>(StateData.START)

    fun getData(context: Context) {
        viewModelScope.launch {
            getData.value = StateData.LOADING
            delay(1000)
            try {
                val users = withContext(Dispatchers.IO) {
                    repository.getData(context)
                }
                getData.value = StateData.SUCCESS(users)
            } catch (e: Exception) {
                getData.value = StateData.FAILURE(e.message.toString())
            }
        }
    }

    fun deleteRecord(context: Context, userId: Int) {
        viewModelScope.launch {
            repository.deleteRecord(context, userId)
        }
    }

    val getSpecificData = MutableStateFlow<StateData>(StateData.START)

    fun getUserIdData(context: Context, userId: Int) {
        viewModelScope.launch {
            getSpecificData.value = StateData.LOADING
            try {
                val users = withContext(Dispatchers.IO) {
                    repository.getUserIdData(context, userId)
                }
                getSpecificData.value = StateData.SUCCESS(users)

            } catch (e: Exception) {
                getSpecificData.value = StateData.FAILURE(e.message.toString())
            }
        }
    }


    fun updateRecord(context: Context, user: User, username: String = "") {
        viewModelScope.launch {
            repository.updateRecord(context, user)
        }
    }
}