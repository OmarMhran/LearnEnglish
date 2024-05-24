package com.example.learnenglish.ui.home


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnenglish.data.AppPreference
import com.example.learnenglish.data.NetworkConnection
import com.example.learnenglish.data.Resource
<<<<<<< HEAD
import com.example.learnenglish.data.model.Exam
import com.example.learnenglish.data.model.Lesson
import com.example.learnenglish.data.model.Part
=======
import com.example.learnenglish.data.model.*
>>>>>>> ac77176 (Learn English App)
import com.example.learnenglish.utils.Grading
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepo,
    private val networkConnection: NetworkConnection,
    private val appPreference: AppPreference
) : ViewModel() {


    val examLiveData = MutableLiveData<Resource<Exam>>()
    val nextPartLiveData = MutableLiveData<Part>()
    val scoreLiveData = MutableLiveData<String>()
<<<<<<< HEAD
    val lessonsLiveData = MutableLiveData<Resource<MutableList<Lesson>>>()
=======
    val lessonsLiveData = MutableLiveData<Resource<LessonDocument>>()
    val correctAnswersLiveData = MutableLiveData<ArrayList<Question>>()
    val lessonExamLiveData = MutableLiveData<Resource<LessonExamDocument>>()

>>>>>>> ac77176 (Learn English App)

    init {
        fetchEnglishExam()
    }


    fun fetchEnglishExam(): LiveData<Resource<Exam>> {
        when {
            networkConnection.isConnected() -> {
                examLiveData.postValue(Resource.loading(null))

                repository.fetchEnglishExam().addSnapshotListener { value, error ->

                    if (error != null) {
                        examLiveData.postValue(Resource.error(null, error.message.toString()))
                    }
                    if (value != null && value.exists()) {
                        repository.fetchEnglishExam().get()
                            .addOnSuccessListener { documentSnapShot ->
                                val exam = documentSnapShot.toObject(Exam::class.java)
                                Log.d("questions", exam.toString())
                                examLiveData.postValue(Resource.success(exam!!))
                            }
                    } else {
                        examLiveData.postValue(Resource.error(null, "No Data"))

                    }
                }


            }
            else -> {
                examLiveData.postValue(Resource.error(null, "No internet connection"))
            }

        }
        return examLiveData
    }


    fun getNextPart(count: Int): LiveData<Part> {
        nextPartLiveData.postValue(examLiveData.value?.data!!.parts[count])
        return nextPartLiveData
    }


    suspend fun getScoreLevel(score: Int) {
        val level = Grading.getLevel(score)
        scoreLiveData.postValue(level)
        appPreference.saveScore(level)
    }

    suspend fun getLessons(level: String): LiveData<Resource<LessonDocument>> {
        when {
            networkConnection.isConnected() -> {
                lessonsLiveData.postValue(Resource.loading(null))

                repository.fetchEnglishExam().addSnapshotListener { value, error ->

                    if (error != null) {
                        lessonsLiveData.postValue(Resource.error(null, error.message.toString()))
                    }
                    if (value != null && value.exists()) {
                        repository.fetchLessons(level)
                            .addOnSuccessListener { documentSnapShot ->
                                val lesson = documentSnapShot.toObject(LessonDocument::class.java)
                                Log.d("questions", lesson.toString())
                                lessonsLiveData.postValue(Resource.success(lesson!!))
                            }
                    } else {
                        lessonsLiveData.postValue(Resource.error(null, "No Data"))

                    }
                }

            }
            else -> {
                lessonsLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return lessonsLiveData
    }


    fun getLessonExam(lesson: String): LiveData<Resource<LessonExamDocument>>{
        when {
            networkConnection.isConnected() -> {
                lessonExamLiveData.postValue(Resource.loading(null))

                repository.fetchLessonExam(lesson).addSnapshotListener { value, error ->

                    if (error != null) {
                        lessonExamLiveData.postValue(Resource.error(null, error.message.toString()))
                    }
                    if (value != null && value.exists()) {
                        repository.fetchLessonExam(lesson).get()
                            .addOnSuccessListener { documentSnapShot ->
                                val lessonExam = documentSnapShot.toObject(LessonExamDocument::class.java)
                                Log.d("lessonExam", lessonExam.toString())
                                lessonExamLiveData.postValue(Resource.success(lessonExam!!))
                            }
                    } else {
                        lessonExamLiveData.postValue(Resource.error(null, "No Data"))

                    }
                }

            }
            else -> {
                lessonExamLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return lessonExamLiveData
    }

    suspend fun logout() {
        repository.logoutUser()
    }
<<<<<<< HEAD
=======

    fun getCorrectAnswers(correctAnswers: ArrayList<Question>) {
        correctAnswersLiveData.postValue(correctAnswers)
    }
>>>>>>> ac77176 (Learn English App)
}