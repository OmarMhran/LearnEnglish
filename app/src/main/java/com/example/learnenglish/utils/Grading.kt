package com.example.learnenglish.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar



object Grading {

    private val SCORE_LEVELS = arrayOf(
        //< 4
        "Low Level",
        // = 5
        "Intermediate",
        // >5 & <7
        "Upper Medium",
        //= 8
        "Expert"
        )


    fun getLevel(score: Int): String{
        return when{
            (score <= 4) -> SCORE_LEVELS[0];
            score == 5 -> SCORE_LEVELS[1];
            (score>5 && score<=7) -> SCORE_LEVELS[2];
            score >= 8 -> SCORE_LEVELS[3];

            else -> "Out Of Range"
        }


    }

}

