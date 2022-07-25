package com.example.task1.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.task1.database.Task1DB
import com.example.task1.model.User
import kotlinx.coroutines.flow.Flow

class Repository {


    suspend fun insertData(context: Context, data : ArrayList<User>){
        Task1DB.getinstance(context).dao.insertData(data)
    }

     suspend fun getData(context: Context): List<User> {
        return Task1DB.getinstance(context).dao.getData()
    }

    suspend fun deleteRecord(context: Context, userId: Int){
        Task1DB.getinstance(context).dao.deleteRecord(userId)
    }

    suspend fun getUserIdData(context: Context, userId: Int): List<User>{
        return Task1DB.getinstance(context).dao.getUserIdData(userId)
    }

    suspend fun updateRecord(context: Context, user: User){
        Task1DB.getinstance(context).dao.update(user)
    }
}