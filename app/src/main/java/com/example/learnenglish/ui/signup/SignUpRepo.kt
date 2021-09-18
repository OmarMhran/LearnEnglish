package com.example.learnenglish.ui.signup

import com.example.learnenglish.data.FirebaseSource
import com.example.learnenglish.data.model.User

import javax.inject.Inject

class SignUpRepo @Inject constructor(private val fireBaseSource: FirebaseSource) {


    fun signUpUser(email: String, password: String,fullName: String) = fireBaseSource.signUpUser(email, password,fullName)


    fun saveUser2(user: User) =
            fireBaseSource.saveUser2(user)

//    fun fetchUser() = fireBaseSource.fetchUser()
//
//    fun sendForgotPassword(email: String)=fireBaseSource.sendForgotPassword(email)

}