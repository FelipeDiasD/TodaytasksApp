package com.bootcampproject.mytodaytaks.model

data class Tasks (
    val title : String,
    val description: String,
    val date: String,
    val hour: String,
    val id: Int = 0
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tasks

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}