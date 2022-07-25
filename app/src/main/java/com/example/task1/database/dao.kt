package com.example.task1.database

import androidx.room.*
import androidx.room.Dao
import com.example.task1.model.Item
import com.example.task1.model.fav_movie
import com.example.task1.model.User


@Dao
interface Dao {
    //get category data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(userdata: List<User>)

    @Query("select * from User")
    suspend fun getData(): List<User>

     @Query("DELETE FROM User WHERE userID = :userId")
     suspend fun deleteRecord(userId : Int)

    @Query("SELECT * FROM User WHERE userID=:userId")
    suspend fun getUserIdData(userId: Int): List<User>

     @Update()
     suspend fun update(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll ( model : List<Item>)

    @Delete
    suspend fun delete(model : Item)

    @Update
    suspend fun update(model: Item)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM movies")
    suspend fun getMovie() : List<Item>

    @Query("SELECT * FROM fav_movies")
    suspend fun getFavMovies() : List<fav_movie>

    @Insert
    suspend fun  insertFav(model: List<fav_movie>)

    @Query("DELETE FROM fav_movies")
    suspend fun deleteFav()
}