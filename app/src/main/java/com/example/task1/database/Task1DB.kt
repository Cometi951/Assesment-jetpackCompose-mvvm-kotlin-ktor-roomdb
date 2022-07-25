package com.example.task1.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.task1.model.Item
import com.example.task1.model.fav_movie
import com.example.task1.model.Services
import com.example.task1.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Database(
    version = 2,
    entities = [User::class, Services::class, Item::class, fav_movie::class]
)
@TypeConverters(DataConverter::class)
abstract class Task1DB : RoomDatabase() {

    abstract val dao: Dao

    companion object {
        private var INSTANCE: Task1DB? = null

        fun getinstance(context: Context): Task1DB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    Task1DB::class.java,
                    "Task1DB"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class DataConverter{
    @TypeConverter // note this annotation
    fun fromStringValuesList(optionValues: ArrayList<String?>?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter // note this annotation
    fun toStringValuesList(optionValuesString: String?): ArrayList<String>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson<ArrayList<String>>(optionValuesString, type)
    }
}