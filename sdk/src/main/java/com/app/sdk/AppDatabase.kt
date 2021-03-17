package com.app.sdk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.sdk.daos.ProjectDao
import com.app.sdk.daos.TaskDao
import com.app.sdk.entities.ProjectEntity
import com.app.sdk.entities.TaskEntity

@Database(entities = [TaskEntity::class, ProjectEntity::class], version = 2)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun projectDao(): ProjectDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "app-database")
                .fallbackToDestructiveMigration()
                .build()
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