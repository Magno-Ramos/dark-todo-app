package com.app.darktodoapp.features.tasks

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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

class TaskListFragment(
    private val displayTitle: Boolean = true,
    private val projectId: Int? = null
) : Fragment(R.layout.fragment_task_list) {

    private lateinit var parentView: View
    private lateinit var repo: TaskRepository
    private val tasksAdapter = TasksAdapter { task, checked ->
        update(task.copy(complete = checked))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentView = view

        setupView()
        setupTasks()
        setupObserver(view)
    }

    private fun setupView() {
        txt_tasks_title.isVisible = displayTitle

        recycler_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tasksAdapter
        }
    }

    private fun setupTasks() {
        ItemTouchHelper(object : SwipeTaskCallback(requireContext()) {
            override fun getTask(position: Int): Task = tasksAdapter.currentList[position]
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = tasksAdapter.currentList[viewHolder.adapterPosition]
                when (direction) {
                    ItemTouchHelper.RIGHT -> update(task.copy(complete = !task.complete))
                    ItemTouchHelper.LEFT -> removeTask(task)
                }
            }
        }).attachToRecyclerView(recycler_tasks)
    }

    private fun setupObserver(view: View) {
        repo = TaskRepository(view.context.applicationContext)

        val liveData = if (projectId != null) repo.getAllFromProject(projectId) else repo.getAll()
        liveData.observe(viewLifecycleOwner, tasksAdapter::submitList)
    }

    private fun addTask(task: Task) {
        GlobalScope.launch {
            repo.save(task)
        }
    }

    private fun update(task: Task) {
        GlobalScope.launch {
            repo.update(task)
        }
    }

    private fun removeTask(task: Task) {
        GlobalScope.launch {
            repo.delete(task)
            launch(context = Dispatchers.Main) {
                parentView.snack(R.string.task_delete_message).apply {
                    setAction(R.string.undo) { addTask(task) }
                    show()
                }
            }
        }
    }
}