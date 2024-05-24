package com.example.learnenglish.data.model

data class Quiz(
    val id: Int? = null,
    val image: String? = null,
    val question: String? = null,
    val option1: String? = null,
    val option2: String? = null,
    val option3: String? = null,
    val correctAnswer: Int? =null,
    val hint: String? = null,
    val nextLevel : String? = null
){
    constructor() : this(null,null,null,null,null,null,null,null,null)
}
