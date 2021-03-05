package com.app.darktodoapp.features.tasks.adapter

import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.text.set
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.darktodoapp.R
import com.app.sdk.models.Task

class TasksAdapter(private val checkChangeListener: (Task, Boolean) -> Unit) :
    ListAdapter<Task, TasksAdapter.TaskAdapterViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_task, parent, false)
        return TaskAdapterViewHolder(view, checkChangeListener)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        currentList[position]?.let {
            holder.bind(it)
        }
    }

    class TaskAdapterViewHolder(
        itemView: View,
        private val checkChangeListener: (Task, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val checkRadio: CheckBox = itemView.findViewById(R.id.checkbox)
        private val txtDescription: TextView = itemView.findViewById(R.id.txt_task_description)
        fun bind(task: Task) {
            checkRadio.isChecked = task.complete
            txtDescription.text = task.description
            txtDescription.underlineFromBoolean(task.complete)

            checkRadio.setOnClickListener {
                checkChangeListener.invoke(task, checkRadio.isChecked)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task) = (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: Task, newItem: Task) = (oldItem == newItem)
        }
    }
}

private fun TextView.underlineFromBoolean(underline: Boolean) {
    text = SpannableStringBuilder(this.text).apply {
        if (underline) set(IntRange(0, this.length), StrikethroughSpan())
        else removeSpan(StrikethroughSpan())
    }
}

