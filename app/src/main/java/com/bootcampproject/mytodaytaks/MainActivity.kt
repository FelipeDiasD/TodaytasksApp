package com.bootcampproject.mytodaytaks

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bootcampproject.mytodaytaks.databinding.ActivityMainBinding
import com.bootcampproject.mytodaytaks.datasource.TaskDataSource
import com.bootcampproject.mytodaytaks.ui.AddTaskActivity
import com.bootcampproject.mytodaytaks.ui.TasksAdapter

private lateinit var binding: ActivityMainBinding
private val adapter by lazy { TasksAdapter() }

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerTasks.adapter = adapter

        insertListeners()
    }

    private fun insertListeners() {
        binding.floatActionBtnAdd.setOnClickListener{
            startActivityForResult(Intent(this,AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this,AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)        }

        adapter.listenerDelete = {

            TaskDataSource.deleteTask(it)
            updateList()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK)
            binding.recyclerTasks.adapter = adapter
        adapter.submitList(TaskDataSource.getList())
    }

    private fun updateList(){
        val list = TaskDataSource.getList()

            binding.includeEmptyState.emptyState.visibility = if(list.isEmpty()) View.VISIBLE
            else
                View.GONE

        adapter.submitList(list)
    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }


}