package com.app.sdk

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.sdk.daos.ProjectDao
import com.app.sdk.daos.TaskDao
import com.app.sdk.entities.ProjectEntity
import com.app.sdk.entities.TaskEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var projectDao: ProjectDao
    private lateinit var taskDao: TaskDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
        projectDao = db.projectDao()
        taskDao = db.taskDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testWriteAndReadProject() {
        projectDao.saveNow(ProjectEntity("Homework"))
        val list = projectDao.getAllNow()
        assertEquals(1, list.size)
        assertEquals("Homework", list[0].title)
    }

    @Test
    fun writeAndReadWithTasks() {
        projectDao.saveNow(ProjectEntity("My Project"))
        taskDao.saveNow(TaskEntity("Study english", 1))
        val projectWithTasks = projectDao.getAllWithTasksNow()
        assertEquals("My Project", projectWithTasks.projectEntity.title)
        assertEquals("Study english", projectWithTasks.tasks[0].description)
        assertEquals(1, projectWithTasks.tasks.size)
    }
}