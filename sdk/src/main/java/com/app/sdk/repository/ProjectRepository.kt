package com.app.sdk.repository

import android.content.Context
import androidx.lifecycle.map
import com.app.sdk.AppDatabaseProvider
import com.app.sdk.daos.ProjectDao
import com.app.sdk.daos.TaskDao
import com.app.sdk.entities.ProjectEntity
import com.app.sdk.models.Project

class ProjectRepository(applicationContext: Context) {
    private val projectDao: ProjectDao
    private val tasksDao: TaskDao

    init {
        AppDatabaseProvider.getInstance(applicationContext).run {
            projectDao = projectDao()
            tasksDao = taskDao()
        }
    }

    fun allProjectsWithTasks() = projectDao.getAllWithTasks()

    fun getProject(projectId: Int) = projectDao.getProject(projectId).map(::Project)

    fun getProjectWithTasks(projectId: Int) =
        projectDao.getProjectWithTasks(projectId).map {
            if (it != null) Project(it) else null
        }

    suspend fun deleteProject(projectId: Int) {
        tasksDao.deleteAll(projectId)
        projectDao.delete(projectId)
    }

    suspend fun save(name: String) {
        projectDao.save(ProjectEntity((name)))
    }
}