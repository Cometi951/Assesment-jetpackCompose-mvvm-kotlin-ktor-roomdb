package com.example.task1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.task1.database.DataConverter
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "User")
data class User(

    @PrimaryKey()
    var userID: Int,
    var userName: String,
    var memberDate : String,

    @TypeConverters(DataConverter::class)
    var userHobbies : ArrayList<String>,
    var sessionToken: Int,
    //val userRewards : ArrayList<Services>,
)

@Entity(tableName = "Rewards")
data class Services(
    @PrimaryKey(autoGenerate = true)
    var serviceID: Long = 1000,
    var name: String,
    var description: String,
    var serviceRewardTotal: Int,
)