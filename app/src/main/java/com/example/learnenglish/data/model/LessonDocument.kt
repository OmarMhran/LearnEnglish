package com.example.learnenglish.data.model

data class LessonDocument (
    val lessons : List<Lesson>
        ){
    constructor(): this( emptyList())
}