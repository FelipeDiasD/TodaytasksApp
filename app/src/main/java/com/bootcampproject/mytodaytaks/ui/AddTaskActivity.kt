package com.bootcampproject.mytodaytaks.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bootcampproject.mytodaytaks.databinding.ActivityAddTaskBinding
import com.bootcampproject.mytodaytaks.datasource.TaskDataSource
import com.bootcampproject.mytodaytaks.extensions.format
import com.bootcampproject.mytodaytaks.extensions.text
import com.bootcampproject.mytodaytaks.model.Tasks
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*

lateinit var binding: ActivityAddTaskBinding

class AddTaskActivity: AppCompatActivity() {

    //primeiro método pra criar a activity, iniciar o ciclo de vida
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     //inflando o layout e instanciando

        binding = ActivityAddTaskBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)){
           val taskId = intent.getIntExtra(TASK_ID,0)
            TaskDataSource.findById(taskId)?.let {
                binding.textInputLayout.text = it.title
                binding.textInputDescription.text = it.description
                binding.textInputData.text = it.date
                binding.textInputTime.text = it.hour


            }
        }

        insertListeners()



    }

    //funçao para a inserção de data e hora
    private fun insertListeners() {
        binding.textInputData.editText?.setOnClickListener{
           val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timezone = TimeZone.getDefault()
                val offset = timezone.getOffset(Date().time) * -1

                binding.textInputData.text = (Date(it + offset).format())

            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.textInputTime.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder().build()


            timePicker.addOnPositiveButtonClickListener {
                val hour = if(timePicker.hour in 0..9) {
                    "0${timePicker.hour}"
                } else
                    timePicker.hour

                val minute = if(timePicker.minute in 0..9) {
                    "0${timePicker.minute}"
                } else
                    timePicker.minute
                binding.textInputTime.text = "$hour, $minute"
            }
            timePicker.show(supportFragmentManager, "timePickerTag")
        }
        binding.buttonCreate.setOnClickListener{
            val task = Tasks(
                title = binding.textInputLayout.text,
                description = binding.textInputDescription.text ,
                date = binding.textInputData.text,
                hour = binding.textInputTime.text,
                id = intent.getIntExtra(TASK_ID, 0)

            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()

        }

        binding.buttonCancel.setOnClickListener{

            finish()
        }



    }

    companion object{
        const val TASK_ID = "taks_id"
    }

}