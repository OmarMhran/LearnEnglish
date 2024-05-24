package com.example.learnenglish.data

import com.example.learnenglish.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    fun signUpUser(email:String,password:String,fullName:String) =
            firebaseAuth.createUserWithEmailAndPassword(email,password)


    fun signInUser(email: String,password: String) =
            firebaseAuth.signInWithEmailAndPassword(email,password)


    fun saveUser2(user:User)=
            firestore.collection("users").add(user)

    fun fetchUser()=firestore.collection("users").get()

    fun sendForgotPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)


    fun fetchEnglishExam() = firestore.collection("english_test").document("E5bSPrvgnok1JY16pujD")

    fun fetchEnglishLessons(level: String) = firestore.collection("lessons").document(level).get()

    fun fetchLessonExam(lesson: String) = firestore.collection("lesson_exam").document(lesson)


    fun logoutUser() = firebaseAuth.signOut()
}