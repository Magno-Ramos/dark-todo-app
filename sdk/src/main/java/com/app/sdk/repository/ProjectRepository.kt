package com.app.sdk.repository

import android.content.Context
import androidx.lifecycle.map
import com.app.sdk.AppDatabaseProvider
import com.app.sdk.entities.ProjectEntity
import com.app.sdk.models.Project

class ProjectRepository(applicationContext: Context) {
    private val projectDao = AppDatabaseProvider.getInstance(applicationContext).projectDao()

    fun allProjectsWithTasks() = projectDao.getAllWithTasks()

    fun getProject(projectId: Int) = projectDao.getProject(projectId).map(::Project)

    fun getProjectWithTasks(projectId: Int) = projectDao.getProjectWithTasks(projectId).map(::Project)

    suspend fun save(name: String) {
        projectDao.save(ProjectEntity((name)))
    }
}