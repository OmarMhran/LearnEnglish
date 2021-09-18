package com.example.learnenglish.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.data.model.Question

class CorrectChoicesAdapter : RecyclerView.Adapter<CorrectChoicesAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.correct_choices_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questions = differ.currentList[position]

        holder.tvQuestionTitle.text = questions.title
        holder.tvQuestionNumber.text = (position+1).toString()

        var answer = ""
        when(questions.correctAnswer){
            1 ->{
                answer = questions.option1!!
            }
            2 ->{
                answer = questions.option2!!
            }
            3 ->{
                answer = questions.option3!!
            }
            4 ->{
                answer = questions.option4!!
            }
        }

        holder.tvCorrectAnswer.text = answer




    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestionTitle: TextView = itemView.findViewById(R.id.tvQuestionTitleScore)
        val tvQuestionNumber: TextView = itemView.findViewById(R.id.tvQuestionNumberScore)
        val tvCorrectAnswer: TextView = itemView.findViewById(R.id.tvCorrectAnswer)

    }





}