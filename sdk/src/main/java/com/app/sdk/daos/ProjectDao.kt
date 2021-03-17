package com.app.sdk.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.sdk.entities.ProjectEntity
import com.app.sdk.entities.ProjectWithTasksEntity

@Dao
interface ProjectDao {
    @Query("SELECT * FROM ProjectEntity WHERE id = :projectId")
    fun getProject(projectId: Int): LiveData<ProjectEntity>

    @Query("SELECT * FROM ProjectEntity")
    fun getAll(): LiveData<List<ProjectEntity>>

    @Transaction
    @Query("SELECT * FROM ProjectEntity")
    fun getAllWithTasks(): LiveData<List<ProjectWithTasksEntity>>

    @Transaction
    @Query("SELECT * FROM ProjectEntity WHERE id = :projectId")
    fun getProjectWithTasks(projectId: Int): LiveData<ProjectWithTasksEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(project: ProjectEntity)

    @Update
    suspend fun update(project: ProjectEntity)

    @Delete
    suspend fun delete(project: ProjectEntity)

    @Query("SELECT * FROM ProjectEntity")
    fun getAllNow(): List<ProjectEntity>

    @Transaction
    @Query("SELECT * FROM ProjectEntity")
    fun getAllWithTasksNow(): List<ProjectWithTasksEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNow(project: ProjectEntity)
}