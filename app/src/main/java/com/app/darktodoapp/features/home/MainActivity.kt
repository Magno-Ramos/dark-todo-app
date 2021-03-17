package com.app.darktodoapp.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.darktodoapp.R
import com.app.darktodoapp.features.projects.ProjectCreatorFragment
import com.app.darktodoapp.helper.currentFormattedDate
import com.app.darktodoapp.features.tasks.TaskCreatorFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        txt_current_date.text = currentFormattedDate()
        fab_plus.setOnClickListener { showChooseCreationFragment() }
    }

    private fun showTaskCreatorFragment() {
        val taskCreatorFragment = TaskCreatorFragment()
        taskCreatorFragment.show(supportFragmentManager, null)
    }

    private fun showProjectCreatorFragment() {
        val fragment = ProjectCreatorFragment()
        fragment.show(supportFragmentManager, null)
    }

    private fun showChooseCreationFragment() {
        val fragment = ChooseCreationFragment()
        fragment.setCreatorListener {
            fragment.dismiss()

            when(it) {
                CreatorType.TASK -> showTaskCreatorFragment()
                CreatorType.PROJECT -> showProjectCreatorFragment()
            }
        }
        fragment.show(supportFragmentManager, null)
    }
}