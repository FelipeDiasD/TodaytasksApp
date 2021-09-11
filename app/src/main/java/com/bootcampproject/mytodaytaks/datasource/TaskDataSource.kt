package com.bootcampproject.mytodaytaks.datasource

import com.bootcampproject.mytodaytaks.model.Tasks

object TaskDataSource{
    private val list = arrayListOf<Tasks>()


    fun getList() = list.toList()

    fun insertTask(task: Tasks) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        }
        else{
            list.remove(task)
            list.add(task)
        }

    }

    fun findById(taskId: Int) = list.find { it.id == taskId }

    fun deleteTask(task: Tasks) {
        list.remove(task)

    }


}