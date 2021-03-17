package com.app.darktodoapp.features.projects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.app.darktodoapp.R
import com.app.darktodoapp.common.GridSpaceItemDecoration
import com.app.darktodoapp.helper.observe
import com.app.sdk.models.Task
import com.app.sdk.repository.ProjectRepository
import kotlinx.android.synthetic.main.fragment_project_list.*

class ProjectListFragment : Fragment(R.layout.fragment_project_list) {

    private val projectsAdapter = ProjectAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_projects.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = projectsAdapter
            addItemDecoration(GridSpaceItemDecoration(10))
        }

        val repo = ProjectRepository(view.context.applicationContext)
        repo.allProjectsWithTasks().observe(viewLifecycleOwner) {
            projectsAdapter.submitList(it)
            it.forEachIndexed { index, item ->
                val holder = recycler_projects.findViewHolderForAdapterPosition(index) as? ProjectAdapter.ProjectViewModel
                holder?.updateProgress(item.tasks.map(::Task))
            }
        }

        projectsAdapter.projectClickListener = { projectId ->
            val intent = view.context.intentToProjectDetail(projectId)
            startActivity(intent)
        }
    }
}