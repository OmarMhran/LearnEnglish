package com.example.learnenglish.ui.home

import com.example.learnenglish.data.FirebaseSource
import javax.inject.Inject

class HomeRepo @Inject constructor(private val fireBaseSource: FirebaseSource) {

    fun fetchEnglishExam() = fireBaseSource.fetchEnglishExam()

    fun fetchLessons() = fireBaseSource.fetchEnglishLessons()

  suspend  fun logoutUser() = fireBaseSource.logoutUser()
}