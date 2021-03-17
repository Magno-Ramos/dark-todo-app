package com.app.darktodoapp.features.projects

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.app.darktodoapp.R
import com.app.darktodoapp.customview.percentOf
import com.app.darktodoapp.features.tasks.TaskCreatorFragment
import com.app.darktodoapp.features.tasks.TaskListFragment
import com.app.darktodoapp.helper.observe
import com.app.darktodoapp.helper.percent
import com.app.sdk.models.Project
import com.app.sdk.repository.ProjectRepository
import kotlinx.android.synthetic.main.activity_project_detail.*

class ProjectDetailActivity : AppCompatActivity(R.layout.activity_project_detail) {

    private var isTaskFragmentInflated: Boolean = false

    private lateinit var repo: ProjectRepository
    private var projectId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        repo = ProjectRepository(applicationContext)
        projectId = intent.extras?.getInt(PARAM_PROJECT_ID)!!

        repo.getProjectWithTasks(projectId).observe(this, ::setupView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_new_task -> {
                showTaskCreator(); true
            }
            android.R.id.home -> {
                finish(); true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView(project: Project) {
        val tasksCount = project.tasks?.size ?: 0
        val completedTasksCount = project.tasks?.count { it.complete } ?: 0

        txt_project_name.text = project.title
        txt_tasks_count.text = resources.getQuantityString(
            R.plurals.project_task_details,
            tasksCount,
            tasksCount,
            completedTasksCount
        )

        pie_progress.setPercentage(tasksCount.percentOf(completedTasksCount).toFloat())

        if (isTaskFragmentInflated.not()) {
            inflateTasksFragment(projectId)
            isTaskFragmentInflated = true
        }
    }

    private fun inflateTasksFragment(projectId: Int) {
        val tasksFragment = TaskListFragment(displayTitle = false, projectId = projectId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, tasksFragment)
            .commit()
    }

    private fun showTaskCreator() {
        val tasksCreatorFragment = TaskCreatorFragment(projectId)
        tasksCreatorFragment.show(supportFragmentManager, null)
    }
}

fun Context.intentToProjectDetail(projectId: Int) =
    Intent(this, ProjectDetailActivity::class.java).apply {
        putExtra(PARAM_PROJECT_ID, projectId)
    }

private const val PARAM_PROJECT_ID = "PARAM_PROJECT_ID"