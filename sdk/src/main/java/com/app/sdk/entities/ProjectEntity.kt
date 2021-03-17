package com.app.sdk.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.sdk.models.Project

@Entity
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val created: Long
) {
    constructor(project: Project) : this(
        id = project.id ?: 0,
        title = project.title,
        created = project.created
    )

    constructor(title: String): this(
        id = 0,
        title = title,
        created = System.currentTimeMillis()
    )
}