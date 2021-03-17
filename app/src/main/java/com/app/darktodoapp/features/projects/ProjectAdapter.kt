package com.app.darktodoapp.features.projects

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.darktodoapp.R
import com.app.darktodoapp.customview.PieProgress
import com.app.darktodoapp.customview.percentOf
import com.app.darktodoapp.helper.inflate
import com.app.sdk.entities.ProjectWithTasksEntity
import kotlin.random.Random

class ProjectAdapter :
    ListAdapter<ProjectWithTasksEntity, ProjectAdapter.ProjectViewModel>(ProjectDiffCallback()) {

    var projectClickListener: ((projectId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewModel =
        object : ProjectViewModel(parent) {
            override fun onClick(position: Int) {
                projectClickListener?.invoke(currentList[position].projectEntity.id)
            }
        }

    override fun onBindViewHolder(holder: ProjectViewModel, position: Int) =
        holder.bindView(getItem(position))

    abstract class ProjectViewModel(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_view_project)) {
        private val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        private val txtSubtitle: TextView = itemView.findViewById(R.id.txt_subtitle)
        private val pieProgress: PieProgress = itemView.findViewById(R.id.pie_chart)

        abstract fun onClick(position: Int)

        init {
            itemView.setOnClickListener { onClick(adapterPosition) }
        }

        fun bindView(projectEntity: ProjectWithTasksEntity) {
            val tasksCount = projectEntity.tasks.size
            val completedCount = projectEntity.tasks.count { it.complete }

            txtTitle.text = projectEntity.projectEntity.title
            txtSubtitle.text = itemView.resources.getQuantityString(R.plurals.tasks_count, tasksCount, tasksCount)

            val completePercent = tasksCount.percentOf(completedCount)
            pieProgress.setPercentage(completePercent.toFloat())
        }
    }
}

private class ProjectDiffCallback : DiffUtil.ItemCallback<ProjectWithTasksEntity>() {
    override fun areItemsTheSame(
        oldItem: ProjectWithTasksEntity,
        newItem: ProjectWithTasksEntity
    ): Boolean =
        oldItem.projectEntity.id == newItem.projectEntity.id

    override fun areContentsTheSame(
        oldItem: ProjectWithTasksEntity,
        newItem: ProjectWithTasksEntity
    ): Boolean =
        oldItem == newItem
}