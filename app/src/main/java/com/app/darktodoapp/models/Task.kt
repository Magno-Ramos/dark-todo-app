package com.app.darktodoapp.models

data class Task (
    val id: Int?,
    val description: String,
    val complete: Boolean,
    val created: Long
)