package com.app.sdk

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.sdk.daos.TaskDao
import com.app.sdk.entities.TaskEntity
import com.app.sdk.models.Task
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeTaskAndReadInList() {
        val task = Task("Some test", false, System.currentTimeMillis())
        taskDao.saveNow(TaskEntity((task)))
        val list = taskDao.getAllNow()
        assert(list.size == 1)
    }
}