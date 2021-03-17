package com.app.sdk.models

import com.app.sdk.entities.ProjectEntity
import com.app.sdk.entities.ProjectWithTasksEntity

data class Project(
    val id: Int? = null,
    val title: String,
    val created: Long,
    val tasks: List<Task>? = null
) {
    constructor(entity: ProjectEntity) : this(
        id = entity.id,
        title = entity.title,
        created = entity.created
    )

    constructor(entity: ProjectWithTasksEntity) : this(
        id = entity.projectEntity.id,
        title = entity.projectEntity.title,
        created = entity.projectEntity.created,
        tasks = entity.tasks.map(::Task)
    )
}