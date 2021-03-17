package com.app.sdk.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.app.sdk.AppDatabaseProvider
import com.app.sdk.entities.TaskEntity
import com.app.sdk.models.Task

class TaskRepository(applicationContext: Context) {
    private val taskDao = AppDatabaseProvider.getInstance(applicationContext).taskDao()

    fun getAll(): LiveData<List<Task>> = taskDao.getAll().asTaskList()

    fun getAllFromProject(projectId: Int) = taskDao.getAllFromProject(projectId).asTaskList()

    suspend fun save(description: String, projectId: Int?) = taskDao.save(TaskEntity(description, projectId))

    suspend fun save(task: Task) = taskDao.save(TaskEntity(task))

    suspend fun update(task: Task) = taskDao.update(TaskEntity(task))

    suspend fun delete(task: Task) = taskDao.delete(TaskEntity(task))

    private fun LiveData<List<TaskEntity>>.asTaskList() = this.map { it.map(::Task) }
}