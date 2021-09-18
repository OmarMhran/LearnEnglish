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

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    var correct = 0
    var answersSelected1 = false
    var answersSelected2 = false
    var answerSelected = false
    var select = 0
    var checkedList = ArrayList<Int>(2)

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
                .inflate(R.layout.question_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questions = differ.currentList[position]

        holder.radioGroup.clearCheck()
        holder.radioGroup.setOnCheckedChangeListener(null)

        holder.tvQuestionTitle.text = questions.title
        holder.tvQuestionNumber.text = (questions.id).toString()
        holder.radioButton1.text = questions.option1
        holder.radioButton2.text = questions.option2
        holder.radioButton3.text = questions.option3
        holder.radioButton4.text = questions.option4


        holder.radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if(checkedId == R.id.radioButton1){
                select = 1
            } else if (checkedId == R.id.radioButton2){
                select = 2
            }
            else if (checkedId == R.id.radioButton3){
                select = 3
            }
            else if (checkedId == R.id.radioButton4){
                select = 4
            }else{
                select = 0
            }


            if (select.equals(questions.correctAnswer)) {
                correct += 1
            } else {
                correct += 0
            }


            if (select != 0 && select != -1){
                if (holder.adapterPosition == 0){
                    answersSelected1 = true
                }else if (holder.adapterPosition == 1){
                    answersSelected2 = true
                }
            }


            if (answersSelected1 && answersSelected2){
                answerSelected = true
            }
            Log.d("answerSelected1", answersSelected1.toString())
            Log.d("answerSelected2", answersSelected2.toString())
            Log.d("Selected",answerSelected.toString())
            Log.d("correct",correct.toString())

            holder.radioGroup.setOnCheckedChangeListener(null)
        }






    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuestionTitle: TextView = itemView.findViewById(R.id.tvQuestionTitle)
        val tvQuestionNumber: TextView = itemView.findViewById(R.id.tvQuestionNumber)
        val radioButton1: RadioButton = itemView.findViewById(R.id.radioButton1)
        val radioButton2: RadioButton = itemView.findViewById(R.id.radioButton2)
        val radioButton3: RadioButton = itemView.findViewById(R.id.radioButton3)
        val radioButton4: RadioButton = itemView.findViewById(R.id.radioButton4)
        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup)
    }


    fun restSelectedCount (){
        this.answersSelected2 = false
        this.answersSelected1 = false
        this.answerSelected = false
        this.select = 0
    }

    fun nextQuiz(): Boolean{
        return this.answerSelected
    }



}