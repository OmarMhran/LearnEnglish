package com.example.learnenglish.ui.learn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnenglish.R
import com.example.learnenglish.data.model.Lesson
import com.example.learnenglish.utils.MediaPlayerStatus
import com.makeramen.roundedimageview.RoundedImageView


class LessonsAdapter(val mediaPlayerStatus: MediaPlayerStatus) :
    RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

    var selectedItem: Int = 0
    var isplaying = false


    private val differCallback = object : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lesson_list_item, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = differ.currentList[position]
        holder.tvLessonTitle.text = lesson.title
        Glide.with(holder.itemView.context).load(lesson.image)
            .into(holder.ivLessonImage)



        holder.ibPlay.setOnClickListener {
                mediaPlayerStatus.onPlay(lesson)
            }
    }


    fun getImage(imageName: String?, context: Context): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }

    private var onItemClickListener: ((Lesson) -> Unit)? = null


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLessonTitle: TextView = itemView.findViewById(R.id.tvLessonNameTitle)
        val ibPlay: ImageButton = itemView.findViewById(R.id.ibPlayLesson)
        val ivLessonImage: RoundedImageView = itemView.findViewById(R.id.ivLessonImage)

    }

    fun setOnItemClickListener(listener: (Lesson) -> Unit) {
        onItemClickListener = listener
    }

}