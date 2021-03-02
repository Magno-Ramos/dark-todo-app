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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var repository: TaskRepository
    private val tasksAdapter = TasksAdapter { task, checked ->
        GlobalScope.launch {
            repository.update(task.copy(complete = checked))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTasks()
        setupObserver(view)
    }

    private fun setupTasks() {
        recycler_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tasksAdapter
        }
    }

    private fun setupObserver(view: View) {
        repository = TaskRepository(view.context.applicationContext)
        repository.getAll().observe(viewLifecycleOwner, tasksAdapter::submitList)
    }
}