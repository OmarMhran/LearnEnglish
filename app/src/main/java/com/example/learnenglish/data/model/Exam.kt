package com.example.learnenglish.data.model

import kotlinx.android.parcel.Parcelize



data class Exam(
        var name: String? = null,
        var parts: List<Part>
){
        constructor(): this(null, emptyList())
}