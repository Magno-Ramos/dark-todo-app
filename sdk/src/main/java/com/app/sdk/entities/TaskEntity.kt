package com.app.sdk.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.sdk.models.Task

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val complete: Boolean,
    val projectId: Int?,
    val created: Long
) {
    constructor(task: Task) : this(
        id = task.id ?: 0,
        description = task.description,
        complete = task.complete,
        projectId = task.projectId,
        created = task.created
    )
    constructor(description: String, projectId: Int? = null): this(
        id = 0,
        description = description,
        complete = false,
        projectId = projectId,
        created = System.currentTimeMillis()
    )
}