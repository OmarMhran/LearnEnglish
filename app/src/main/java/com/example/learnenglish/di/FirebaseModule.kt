package com.example.learnenglish.di

import android.app.Application
import android.content.Context
import com.example.learnenglish.data.AppPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FireBaseModule {

    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }


    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()


    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ) = AppPreference(context)


}