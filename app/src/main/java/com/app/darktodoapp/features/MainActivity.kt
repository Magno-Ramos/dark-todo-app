package com.app.darktodoapp.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.darktodoapp.R
import com.app.darktodoapp.helper.currentFormattedDate
import com.app.darktodoapp.features.tasks.TaskCreatorFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        txt_current_date.text = currentFormattedDate()
        fab_plus.setOnClickListener { showTaskCreatorFragment() }
    }

    private fun showTaskCreatorFragment() {
        val taskCreatorFragment = TaskCreatorFragment()
        taskCreatorFragment.show(supportFragmentManager, null)
    }
}