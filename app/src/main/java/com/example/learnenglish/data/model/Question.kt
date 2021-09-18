package com.example.learnenglish.data.model




data class Question(
        var id: Int? = null,
        val title: String? = null,
        val option1: String? = null,
        val option2: String? = null,
        val option3: String? = null,
        val option4: String? = null,
        val correctAnswer: Int? = null
){
        constructor() : this(null,null,null,null,null,null,null)

}
