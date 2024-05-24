package com.example.learnenglish.utils

import com.example.learnenglish.data.model.Lesson

interface MediaPlayerStatus {
    fun onPlay(lesson: Lesson)
    fun onStop(position: Int)
}