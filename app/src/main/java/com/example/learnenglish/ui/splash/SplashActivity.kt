package com.example.learnenglish.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.asLiveData
import com.example.learnenglish.R
import com.example.learnenglish.data.AppPreference
import com.example.learnenglish.ui.home.HomeActivity
import com.example.learnenglish.ui.learn.LearnActivity
import com.example.learnenglish.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_learn.*
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            this.finish()

        }, 2000)
    }
}