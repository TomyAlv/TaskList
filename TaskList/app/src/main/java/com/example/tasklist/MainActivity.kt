package com.example.tasklist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklist.adapters.TaskAdapter
import com.example.tasklist.databinding.ActivityMainBinding
import com.example.tasklist.models.Task
import com.example.tasklist.viewmodels.TaskViewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    companion object {
        const val REQUEST_CODE_ADD_TASK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        setupRecyclerView()

        binding.newTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK)
        }

        taskViewModel.tasks.observe(this) { tasks ->
            taskAdapter.submitList(tasks)
        }
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter()
        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
            data?.let {
                val taskName = it.getStringExtra("TASK_NAME")
                val taskDesc = it.getStringExtra("TASK_DESC")
                if (taskName != null && taskDesc != null) {
                    val newTask = Task(taskName, taskDesc, taskName)
                    taskViewModel.addTask(newTask)
                }
            }
        }
    }
}
