package com.example.learnenglish.ui.learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.learnenglish.R
import com.example.learnenglish.data.Status
import com.example.learnenglish.data.model.Quiz
import com.example.learnenglish.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_learn.*
import kotlinx.android.synthetic.main.activity_lesson_exam.*
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.android.synthetic.main.fragment_lesson.btnOption1
import kotlinx.android.synthetic.main.fragment_lesson.btnOption2
import kotlinx.android.synthetic.main.fragment_lesson.btnOption3
import kotlinx.android.synthetic.main.fragment_lesson.btnToNextLesson
import kotlinx.android.synthetic.main.fragment_lesson.ibHint
import kotlinx.android.synthetic.main.fragment_lesson.ivExamImage
import kotlinx.android.synthetic.main.fragment_lesson.tvQuestionExam
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random.Default.nextInt

@AndroidEntryPoint
class LessonExamActivity : AppCompatActivity() {


    lateinit var quiz: Quiz
    private val viewModel: HomeViewModel by viewModels()
    var buttonList = listOf<AppCompatButton>()
    var examPassed = false
    var level = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_exam)


        setSupportActionBar(tblessonExam)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        tbTitleLessonExam.text = "Test"

        val lesson = intent.getStringExtra("lesson");





        lifecycleScope.launch {
            viewModel.getLessonExam(lesson!!)
            viewModel.lessonExamLiveData.observe(this@LessonExamActivity, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        pbLessonExam.visibility = View.VISIBLE
                    }

                    Status.SUCCESS -> {
                        val examlist = it.data?.examlist
                        var randomValue = (1..4).random()
                        Log.d("getRandomQuiz: ", randomValue.toString())
                        var randomExam = examlist?.get(randomValue)
                        setView(randomExam!!)
                        quiz = randomExam
                        pbLessonExam.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        pbLessonExam.visibility = View.GONE
                        Toast.makeText(this@LessonExamActivity, it.message!!, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }






        ibHint.setOnClickListener {
            Toast.makeText(this, quiz.hint, Toast.LENGTH_SHORT).show()
        }


        buttonList = listOf<AppCompatButton>(btnOption1, btnOption2, btnOption3)

        btnOption1.setOnClickListener {
            btnOption2.isEnabled = false
            btnOption3.isEnabled = false
            if (quiz.correctAnswer == 1) {
                btnOption1.setBackgroundResource(R.drawable.correct_button_background)
                examPassed = true
            } else {
                btnOption1.setBackgroundResource(R.drawable.wrong_button_background)
                buttonList.get(quiz.correctAnswer!! - 1)
                    .setBackgroundResource(R.drawable.correct_button_background)
            }
        }

        btnOption2.setOnClickListener {
            btnOption1.isEnabled = false
            btnOption3.isEnabled = false
            if (quiz.correctAnswer == 2) {
                btnOption2.setBackgroundResource(R.drawable.correct_button_background)
                examPassed = true
            } else {
                btnOption2.setBackgroundResource(R.drawable.wrong_button_background)
                buttonList.get(quiz.correctAnswer!! - 1)
                    .setBackgroundResource(R.drawable.correct_button_background)
            }
        }

        btnOption3.setOnClickListener {
            btnOption2.isEnabled = false
            btnOption1.isEnabled = false
            if (quiz.correctAnswer == 3) {
                btnOption3.setBackgroundResource(R.drawable.correct_button_background)
                examPassed = true
            } else {
                btnOption3.setBackgroundResource(R.drawable.wrong_button_background)
                buttonList.get(quiz.correctAnswer!! - 1)
                    .setBackgroundResource(R.drawable.correct_button_background)
            }
        }



        btnToNextLesson.setOnClickListener {
            if (examPassed) {
                if (quiz.nextLevel == "Done") {
                    Toast.makeText(
                        this,
                        "Congratulation , You have Passed All Exams",
                        Toast.LENGTH_LONG
                    ).show()

                    toLessonActivity(lesson!!)
                }else{
                    toLessonActivity(quiz.nextLevel!!)
                }

            } else {
                Toast.makeText(
                    this,
                    "You didn't passed the exam",
                    Toast.LENGTH_LONG
                ).show()
                toLessonActivity(lesson!!)
            }

        }

    }

    private fun setView(quiz: Quiz) {
        tvQuestionExam.text = quiz.question
        Glide.with(this).load(quiz.image!!)
            .into(ivExamImage)
        btnOption1.text = quiz.option1
        btnOption2.text = quiz.option2
        btnOption3.text = quiz.option3

    }


//    private fun getImage(imageName: String): Int {
//        return this.resources.getIdentifier(imageName, "drawable", this.packageName)
//    }

    private fun getRandomQuiz(list:List<Quiz> ): Quiz {
        var randomValue = (list.indices).random()
        Log.d("getRandomQuiz: ", randomValue.toString())
        return list[randomValue]
    }

    private fun toLessonActivity(lesson: String) {
        val intent = Intent(this, LearnActivity::class.java)
        intent.putExtra("level", lesson)
        startActivity(intent)
        this.finish()
    }
}