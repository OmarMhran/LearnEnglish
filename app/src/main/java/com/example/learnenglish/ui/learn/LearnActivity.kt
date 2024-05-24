package com.example.learnenglish.ui.learn

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnenglish.R
import com.example.learnenglish.data.AppPreference
import com.example.learnenglish.data.Status
import com.example.learnenglish.data.model.Lesson
import com.example.learnenglish.data.model.LessonDocument
import com.example.learnenglish.data.model.LessonExamDocument
import com.example.learnenglish.data.model.Quiz
import com.example.learnenglish.ui.home.HomeViewModel
import com.example.learnenglish.utils.MediaPlayerStatus
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_learn.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LearnActivity : AppCompatActivity(), MediaPlayerStatus {
    @Inject
    lateinit var appPreference: AppPreference
    lateinit var lessonsAdapter: LessonsAdapter
    private val viewModel: HomeViewModel by viewModels()
    var lesson = ""
    var level = ""

    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)


        level = intent.getStringExtra("level")!!

        setSupportActionBar(tbLearn)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setUpRecyclerview()



//        appPreference.userScoreFlow.asLiveData().observe(this, { name ->
//            if (name.isNotEmpty()) {
//                tbTitle.text = name
//                lesson = name
//            }
//        })
        tbTitle.text = level

        mediaPlayer = MediaPlayer()

        lifecycleScope.launch {
            viewModel.getLessons(level)
            viewModel.lessonsLiveData.observe(this@LearnActivity, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        pbLearn.visibility = View.VISIBLE
                    }

                    Status.SUCCESS -> {
                       lessonsAdapter.differ.submitList(it.data?.lessons!!)
                        lesson = it.data.lessons[1].level!!
                        pbLearn.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        pbLearn.visibility = View.GONE
                        Toast.makeText(this@LearnActivity, it.message!!, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }


        btnToTest.setOnClickListener {
            toLessonExamActivity(lesson)
        }


    }

    private fun setUpRecyclerview() {
        lessonsAdapter = LessonsAdapter(this)
        rvLessons.apply {
            adapter = lessonsAdapter
            layoutManager =
                LinearLayoutManager(this@LearnActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onPlay(lesson: Lesson) {

        var audio = resources.getIdentifier(lesson.lecture, "raw", this.packageName)
        var audioUri = Uri.parse(lesson.lecture)
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            mediaPlayer = MediaPlayer.create(applicationContext, audio)
            mediaPlayer.start()

            mediaPlayer.setOnCompletionListener {
                it.stop()
            }
        }

        Toast.makeText(this, lesson.title, Toast.LENGTH_SHORT).show()
    }

    override fun onStop(position: Int) {
        lessonsAdapter.isplaying = false
    }

    private fun toLessonExamActivity(lesson: String) {
        val intent = Intent(this, LessonExamActivity::class.java)
        intent.putExtra("lesson", lesson)
        startActivity(intent)
        this.finish()
    }


}

fun View.showsnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}