package com.app.sdk.models

import com.app.sdk.entities.TaskEntity

data class Task(
    val id: Int?,
    val description: String,
    val complete: Boolean,
    val created: Long
) {
    constructor(entity: TaskEntity) : this(
        entity.id,
        entity.description,
        entity.complete,
        entity.created
    )

    constructor(description: String, complete: Boolean, created: Long) : this(
        null,
        description,
        complete,
        created
    )
}