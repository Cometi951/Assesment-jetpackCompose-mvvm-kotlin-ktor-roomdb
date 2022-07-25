package com.example.shyama.android.database

import android.util.Log
import com.example.moviesapp.apiservice.Api
import com.example.task1.model.FavMoviesModel
import com.example.task1.model.InfosModel
import com.example.task1.model.MoviesModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object ApiService {

    val client: HttpClient = HttpClient() {
        // Logging
        install(Logging) {
            level = LogLevel.ALL
        }
        expectSuccess = true
        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 5000L
            connectTimeoutMillis = 5000L
            socketTimeoutMillis = 5000L
        }
        install(HttpRequestRetry) {
            retryOnExceptionIf(maxRetries = 5) { request, cause ->
                cause is HttpRequestTimeoutException

            }
            delayMillis { retry ->
                retry * 500L
            } // retries in 3, 6, 9, etc. seconds
        }
        install(ContentNegotiation) {
            var converter = KotlinxSerializationConverter(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
            register(ContentType.Application.Json, converter)
        }
    }

    suspend fun getTopMovies(): MoviesModel? {
       return try {
            val response = client.get(Api.BASE_URL+"/movie/top_rated") {
                parameter("api_key", "4f7ec971553adf8f4b7c584cc0d4ce7e")
            }.body<MoviesModel>()
           response
        } catch (e: Exception) {
            Log.d("error", "get Data response: ${e.message}")
            null
        }
    }
    suspend fun getFavourite(): FavMoviesModel? {
       return try {
            val response = client.get(Api.BASE_URL+"/list/${Api.LIST_ID}") {
                parameter("api_key", "4f7ec971553adf8f4b7c584cc0d4ce7e")
            }.body<FavMoviesModel>()
           response
        } catch (e: Exception) {
            Log.d("error", "get Data response: ${e.message}")
            null
        }
    }
    suspend fun getMovieInfo(movieId:String): InfosModel? {
       return try {
            val response = client.get(Api.BASE_URL+"/movie/$movieId") {
                parameter("api_key", "4f7ec971553adf8f4b7c584cc0d4ce7e")
            }.body<InfosModel>()
           response
        } catch (e: Exception) {
            Log.d("error", "get Data response: ${e.message}")
            null
        }
    }
    suspend fun addFavourite(movieId:String) {
        try {
            val response = client.post(Api.BASE_URL+"/list/${Api.LIST_ID}/add_item") {
                parameter("api_key", "4f7ec971553adf8f4b7c584cc0d4ce7e")
                parameter("session_id", "86d7cb01c510413cf85dce2daf684d87622a5626")
                contentType(ContentType.Application.Json)
                setBody(movieIdModel(movieId.toInt()))
            }

        } catch (e: Exception) {
            Log.d("error", "get Data response: ${e.message}")
            null
        }
    }
    suspend fun removeFavourite(movieId:String) {
        try {
            val response = client.post(Api.BASE_URL+"/list/${Api.LIST_ID}/remove_item") {
                parameter("api_key", "4f7ec971553adf8f4b7c584cc0d4ce7e")
                parameter("session_id", "86d7cb01c510413cf85dce2daf684d87622a5626")
                contentType(ContentType.Application.Json)
                setBody(movieIdModel(movieId.toInt()))
            }

        } catch (e: Exception) {
            Log.d("error", "get Data response: ${e.message}")
            null
        }
    }
}

@Serializable
data class movieIdModel(
    var media_id: Int ,
)
@Serializable
data class Customer(
    var api_key: String = "",
    var request_token: String = ""
)
