package com.example.learnenglish.app

import android.app.Application
import android.content.Context
import com.example.learnenglish.data.AppPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()


    }
}