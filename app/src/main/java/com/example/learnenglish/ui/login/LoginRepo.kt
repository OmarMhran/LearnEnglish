package com.example.learnenglish.ui.login

import com.example.learnenglish.data.FirebaseSource
import com.example.learnenglish.data.model.User
import javax.inject.Inject

class LoginRepo @Inject constructor(private val fireBaseSource: FirebaseSource) {

     fun signInUser(email: String, password: String) = fireBaseSource.signInUser(email, password)


    fun fetchUser() = fireBaseSource.fetchUser()

    fun sendForgotPassword(email: String)=fireBaseSource.sendForgotPassword(email)

}