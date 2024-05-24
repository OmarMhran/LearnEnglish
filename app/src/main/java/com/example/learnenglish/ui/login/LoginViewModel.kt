package com.example.learnenglish.ui.login

import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnenglish.data.NetworkConnection
import com.example.learnenglish.data.Resource
import com.example.learnenglish.data.model.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject  constructor(
        private val repository: LoginRepo,
        private val networkConnection: NetworkConnection,
        private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val userLiveData = MutableLiveData<Resource<User>>()
    private val sendResetPasswordLiveData = MutableLiveData<Resource<User>>()


    @RequiresApi(Build.VERSION_CODES.N)
    fun signInUser(email: String, password: String): LiveData<Resource<User>> {
        when {
            TextUtils.isEmpty(email) || TextUtils.isEmpty(password) -> {
                userLiveData.postValue(Resource.error(null, "Enter email and password"))
            }

            !isEmailValid(email) ->{
                userLiveData.postValue(Resource.error(null, "Please enter valid email"))
            }
            email != email.lowercase() -> {
                userLiveData.postValue(Resource.error(null, "Email Shouldn't Contain Capital Case Letter"))
            }

            networkConnection.isConnected() -> {
                userLiveData.postValue(Resource.loading(null))

                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                    //check if email exists
                    if (it.result?.signInMethods?.size == 0) {
                        userLiveData.postValue(Resource.error(null, "Email does not exist"))
                    } else {

                        repository.signInUser(email, password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                repository.fetchUser().addOnCompleteListener { userTask ->
                                    if (userTask.isSuccessful) {
                                        userTask.result?.documents?.forEach {

                                            if (it.data!!["email"]?.equals(email)!!) {
                                                var user = User()
                                                user = it.toObject(User::class.java)!!
                                                val name = it.data?.get("username")
                                                userLiveData.postValue(Resource.success(user))
                                            }
                                        }
                                    } else {
                                        userLiveData.postValue(Resource.error(null, userTask.exception?.message.toString()))
                                    }
                                }

                            } else {
                                userLiveData.postValue(Resource.error(null, task.exception?.message.toString()))

                            }
                        }
                    }
                }
            }
            else -> {
                userLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return userLiveData
    }


    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun sendResetPassword(email: String): LiveData<Resource<User>> {

        when {
            TextUtils.isEmpty(email) -> {
                sendResetPasswordLiveData.postValue(Resource.error(null, "Enter registered email"))
            }
            networkConnection.isConnected() -> {
                repository.sendForgotPassword(email).addOnCompleteListener { task ->
                    sendResetPasswordLiveData.postValue(Resource.loading(null))
                    if (task.isSuccessful) {
                        sendResetPasswordLiveData.postValue(Resource.success(User()))
                    } else {
                        sendResetPasswordLiveData.postValue(
                                Resource.error(
                                        null,
                                        task.exception?.message.toString()
                                )
                        )
                    }
                }
            }
            else -> {
                sendResetPasswordLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return sendResetPasswordLiveData
    }
}