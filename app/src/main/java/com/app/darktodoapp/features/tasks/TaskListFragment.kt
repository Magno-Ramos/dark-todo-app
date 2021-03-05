package com.app.darktodoapp.features.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.darktodoapp.R
import com.app.darktodoapp.helper.observe
import com.app.darktodoapp.helper.snack
import com.app.darktodoapp.features.tasks.adapter.TasksAdapter
import com.app.sdk.models.Task
import com.app.sdk.repository.TaskRepository
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var parentView: View
    private lateinit var repository: TaskRepository
    private val tasksAdapter = TasksAdapter { task, checked ->
        GlobalScope.launch {
            repository.update(task.copy(complete = checked))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentView = view
        setupTasks()
        setupObserver(view)
    }

    private fun setupTasks() {
        recycler_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tasksAdapter
        }

        ItemTouchHelper(object : SwipeTaskCallback(requireContext()) {
            override fun getTask(position: Int): Task = tasksAdapter.currentList[position]
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = tasksAdapter.currentList[viewHolder.adapterPosition]
                when (direction) {
                    ItemTouchHelper.RIGHT -> toggleTaskCompletion(task)
                    ItemTouchHelper.LEFT -> removeTask(task)
                }
            }
        }).attachToRecyclerView(recycler_tasks)
    }

    private fun setupObserver(view: View) {
        repository = TaskRepository(view.context.applicationContext)
        repository.getAll().observe(viewLifecycleOwner, tasksAdapter::submitList)
    }

    private fun addTask(task: Task) {
        GlobalScope.launch {
            repository.save(task)
        }
    }

    private fun toggleTaskCompletion(task: Task) {
        GlobalScope.launch {
            repository.update(task.copy(complete = task.complete.not()))
        }
    }

    private fun removeTask(task: Task) {
        GlobalScope.launch {
            repository.delete(task)
            launch(context = Dispatchers.Main) {
                parentView.snack(R.string.task_delete_message).apply {
                    setAction(R.string.undo) { addTask(task) }
                    show()
                }
            }
        }
    }
}