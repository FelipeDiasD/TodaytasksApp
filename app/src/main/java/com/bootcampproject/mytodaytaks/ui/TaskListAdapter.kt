package com.bootcampproject.mytodaytaks.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bootcampproject.mytodaytaks.R
import com.bootcampproject.mytodaytaks.databinding.ActivityMainBinding
import com.bootcampproject.mytodaytaks.databinding.ItemTaskBinding
import com.bootcampproject.mytodaytaks.model.Tasks


class TasksAdapter: ListAdapter<Tasks, TasksAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerDelete: (Tasks) -> Unit = {}
    var listenerEdit: (Tasks) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Tasks) {
            binding.taskTitle.text = item.title
            binding.taskDate.text = "${item.date}+${item.hour}"
            binding.imageViewOptions.setOnClickListener{
                showPopUp(item)
            }

        }

        private fun showPopUp(item: Tasks) {
            val imageView = binding.imageViewOptions
            val popupMenu =PopupMenu (imageView.context, imageView)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}



        class DiffCallback: DiffUtil.ItemCallback<Tasks>(){
            override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks) = oldItem.id == newItem.id
        }