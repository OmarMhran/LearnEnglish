package com.example.learnenglish.ui.home


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnenglish.data.AppPreference
import com.example.learnenglish.data.NetworkConnection
import com.example.learnenglish.data.Resource
import com.example.learnenglish.data.model.Exam
import com.example.learnenglish.data.model.Lesson
import com.example.learnenglish.data.model.Part
import com.example.learnenglish.utils.Grading


class HomeViewModel @ViewModelInject constructor(
        private val repository: HomeRepo,
        private val networkConnection: NetworkConnection,
        private val appPreference: AppPreference
) : ViewModel() {


    val examLiveData = MutableLiveData<Resource<Exam>>()
    val nextPartLiveData = MutableLiveData<Part>()
    val scoreLiveData = MutableLiveData<String>()
    val lessonsLiveData = MutableLiveData<Resource<MutableList<Lesson>>>()

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
                        repository.fetchEnglishExam().get().addOnSuccessListener { documentSnapShot ->
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

    suspend fun getLessons() {
        when {
            networkConnection.isConnected() -> {
                lessonsLiveData.postValue(Resource.loading(null))

                repository.fetchLessons().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val list = ArrayList<Lesson>()
                            for (document in task.result!!) {
                                val lesson = document.toObject(Lesson::class.java)
                                list.add(lesson)
                                Log.d("lessons", lesson.toString())

                                lessonsLiveData.postValue(Resource.success(list))
                            }

                        }

                    } else {
                        lessonsLiveData.postValue(Resource.error(null, "No Data"))

                    }

                }

            }
            else -> {
                examLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
    }

    suspend fun logout(){
        repository.logoutUser()
    }
}