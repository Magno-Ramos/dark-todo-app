package com.app.sdk.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.sdk.models.Task

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val complete: Boolean,
    val created: Long
) {
    constructor(task: Task) : this(
        id = task.id ?: 0,
        description = task.description,
        complete = task.complete,
        created = task.created
    )
    constructor(description: String): this(
        id = 0,
        description = description,
        complete = false,
        created = System.currentTimeMillis()
    )
}