package com.example.tasklist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasklist.models.Task

class TaskViewModel : ViewModel() {
    val name = MutableLiveData<String>()
    val desc = MutableLiveData<String>()
    val tasks = MutableLiveData<List<Task>>()
    private val taskList = mutableListOf<Task>()

    fun addTask(task: Task) {
        taskList.add(task)
        tasks.value = taskList
    }
}
