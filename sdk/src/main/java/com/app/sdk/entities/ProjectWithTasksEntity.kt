package com.app.sdk.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectWithTasksEntity(
    @Embedded val projectEntity: ProjectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )
    val tasks: List<TaskEntity>
)