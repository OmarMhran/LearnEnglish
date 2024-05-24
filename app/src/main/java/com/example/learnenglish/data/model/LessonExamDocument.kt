package com.example.learnenglish.data.model

data class LessonExamDocument(
    val examlist : List<Quiz>
){
    constructor():this(emptyList())
}
