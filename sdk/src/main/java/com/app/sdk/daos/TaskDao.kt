package com.app.sdk.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.sdk.entities.TaskEntity

@Dao
internal interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    fun getAll(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE projectId = :projectId")
    fun getAllFromProject(projectId: Int): LiveData<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE projectId = :projectId")
    suspend fun deleteAll(projectId: Int)

    @Query("SELECT * FROM TaskEntity")
    fun getAllNow(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNow(task: TaskEntity)
}