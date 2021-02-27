package com.app.darktodoapp.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.darktodoapp.R
import com.app.darktodoapp.helper.observe
import com.app.darktodoapp.tasks.adapter.TasksAdapter
import com.app.sdk.repository.TaskRepository
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val tasksAdapter = TasksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tasksAdapter
        }
    }
}