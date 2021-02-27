package com.app.sdk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.sdk.daos.TaskDao
import com.app.sdk.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()
    }
}