package com.example.learnenglish.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar



object Grading {

    private val SCORE_LEVELS = arrayOf(
        //< 4
        "Beginner",
        // <=5
        "Intermediate",
        //= 8
        "Advanced"
        )


    fun getLevel(score: Int): String{
        return when{
            (score <= 4) -> SCORE_LEVELS[0];
            (score>=5 && score<=7) -> SCORE_LEVELS[1];
            score >= 8 -> SCORE_LEVELS[2];

            else -> "Out Of Range"
        }


    }

}

