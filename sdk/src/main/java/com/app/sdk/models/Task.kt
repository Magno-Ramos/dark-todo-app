package com.app.sdk.models

import com.app.sdk.entities.TaskEntity

data class Task(
    val id: Int?,
    val description: String,
    val complete: Boolean,
    val projectId: Int?,
    val created: Long
) {
    constructor(entity: TaskEntity) : this(
        id = entity.id,
        description = entity.description,
        complete = entity.complete,
        projectId = entity.projectId,
        created = entity.created
    )

    constructor(
        description: String,
        complete: Boolean,
        created: Long,
        projectId: Int? = null
    ) : this(
        id = null,
        description = description,
        complete = complete,
        projectId = projectId,
        created = created
    )
}