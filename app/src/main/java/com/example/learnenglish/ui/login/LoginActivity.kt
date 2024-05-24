package com.example.learnenglish.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData

import com.example.learnenglish.R
import com.example.learnenglish.data.AppPreference
import com.example.learnenglish.data.Status
import com.example.learnenglish.databinding.ActivityLoginBinding
import com.example.learnenglish.ui.signup.SignUpActivity
import com.example.learnenglish.ui.home.HomeActivity
import com.example.learnenglish.ui.learn.LearnActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel
    @Inject
    lateinit var appPreference: AppPreference

    // private var binding: ActivityLoginBinding? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding?.root)

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)




        binding.btnLogin.setOnClickListener {
            val emailText = binding?.etEmailLogin?.text?.toString()?.trim()
            val passwordText = binding?.etPasswordLogin?.text?.toString()?.trim()
            viewModel.signInUser(emailText!!, passwordText!!).observe(this, {
                when (it.status) {
                    Status.LOADING -> {
                        binding.pbLogin.visibility = View.VISIBLE
                    }

                    Status.SUCCESS -> {
                        binding.pbLogin.visibility = View.GONE
                        binding.root.showsnackBar("Login successful")
                        val user = it.data
                        toHome(user?.username!!,user?.email!!)
                    }

                    Status.ERROR -> {
                        binding.pbLogin.visibility = View.GONE
                        binding.root.showsnackBar(it.message!!)
                    }
                }
            })

        }


        binding.btnToSignUp.setOnClickListener {
            toSignUp()
        }
    }

    private fun toSignUp(){
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun toHome(username: String, email: String){
        appPreference.userScoreFlow.asLiveData().observe(this,{ score ->
            if (score.isNullOrEmpty()){
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.putExtra("username",username)
                intent.putExtra("email",email)
                startActivity(intent)
                this.finish()
            }else{
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        })

    }
}



fun View.showsnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}