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
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "app-database").build()
    }
}

internal object AppDatabaseProvider {
    private var appDatabase: AppDatabase? = null
    fun getInstance(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = AppDatabase.buildDatabase(context)
        }
        return appDatabase!!
    }
}