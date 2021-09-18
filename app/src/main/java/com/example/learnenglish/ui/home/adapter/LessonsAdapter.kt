package com.example.learnenglish.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnenglish.data.model.Lesson
import com.example.learnenglish.databinding.LessonItemBinding


class LessonsAdapter : RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    var selectedItem: Int = 0

    private val differCallback = object : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsAdapter.LessonsViewHolder {
        return LessonsViewHolder(LessonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: LessonsAdapter.LessonsViewHolder, position: Int) {
        val lesson = differ.currentList[position]
        holder.bind(lesson)
    }


    private var onItemClickListener: ((Lesson) -> Unit)? = null

    inner class LessonsViewHolder(private val binding: LessonItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(lesson: Lesson) {
            binding.tvLessonName.text = lesson.title
            Glide.with(binding.root.context).load(lesson.image).into(binding.ivLesson)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(lesson)
                }
            }
        }
    }


    fun setOnItemClickListener(listener: (Lesson) -> Unit){
        onItemClickListener = listener
    }

}