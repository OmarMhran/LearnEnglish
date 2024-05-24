package com.example.learnenglish.data.model

import java.io.Serializable

data class Lesson (
        var id: Int? = null,
        val title: String? = null,
        val lecture: String? = null,
        val image: String? = null,
        val level: String? = null) : Serializable

{

    constructor() : this(null,null,null,null,null)
}