package com.example.learnenglish.data

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreference(context: Context){

    private val appPreference = context.createDataStore("data_prefs")

    suspend fun saveUser(name :String, email:String){
        appPreference.edit {
            it[USER_NAME] = name
            it[USER_EMAIL] = email
        }
    }

    suspend fun saveScore(score :String){
        appPreference.edit {
            it[USER_SCORE] = score
        }
    }

    val userNameFlow: Flow<String> = appPreference.data.map {
        it[USER_NAME] ?: " "
    }

    val userEmailFlow: Flow<String> = appPreference.data.map {
        it[USER_EMAIL] ?: " "
    }

    val userScoreFlow: Flow<String> = appPreference.data.map {
        it[USER_SCORE] ?: ""
    }


    companion object{
        val USER_NAME = preferencesKey<String>("USER_NAME")
        val USER_EMAIL = preferencesKey<String>("USER_EMAIL")

        val USER_SCORE = preferencesKey<String>("USER_SCORE")

    }
}