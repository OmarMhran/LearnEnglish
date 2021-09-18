package com.example.learnenglish.data.model

import kotlinx.android.parcel.Parcelize


data class Part(
        var id: Int? = null,
        var questions: List<Question>
        ){
        constructor() :this(null, emptyList())
}