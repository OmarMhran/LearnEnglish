package com.example.learnenglish.ui.home

import com.example.learnenglish.data.FirebaseSource
import javax.inject.Inject

class HomeRepo @Inject constructor(private val fireBaseSource: FirebaseSource) {

    fun fetchEnglishExam() = fireBaseSource.fetchEnglishExam()

    fun fetchLessons(level: String) = fireBaseSource.fetchEnglishLessons(level)

    fun fetchLessonExam(lesson: String) = fireBaseSource.fetchLessonExam(lesson)


    suspend  fun logoutUser() = fireBaseSource.logoutUser()
}