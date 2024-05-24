package com.example.learnenglish.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.learnenglish.R
import com.example.learnenglish.data.Status
import com.example.learnenglish.data.model.User
import com.example.learnenglish.databinding.ActivitySignUpBinding
import com.example.learnenglish.ui.home.HomeActivity
import com.example.learnenglish.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {


    lateinit var viewModel: SignUpViewModel

    @Inject
    lateinit var auth: FirebaseAuth
    lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
                DataBindingUtil.setContentView<ActivitySignUpBinding>(this, R.layout.activity_sign_up)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)


        binding.btnToSignIn.setOnClickListener {
            toSignIn()
        }




        binding?.btnSignUp?.setOnClickListener {
            val emailText = binding.etEmailSignUp.text?.toString()
            val passwordText = binding.etPasswordSignUp.text.toString()
            val fullNameText = binding.etNameSignUp.text?.toString()
            val nationalIdText = binding.etNationalIdSignUp.text?.toString()
            val mobilePhoneText = binding.etMobileSignUp.text?.toString()
            val confirmPasswordText = binding.etConfirmPasswordSignUp.text?.toString()


            when {
                nationalIdText?.length!! != 12 ->
                    binding.root.rootView.showsnackBar("Civil Id must be 12 digit")

                mobilePhoneText?.length!! != 8 ->
                    binding.root.rootView.showsnackBar("Mobile Phone must be 8 digit")

                !confirmPasswordText?.equals(passwordText)!! ->
                    binding.root.rootView.showsnackBar("Passwords are not equal")


                else -> {
                    user = User(fullNameText!!, emailText!!, nationalIdText, mobilePhoneText, passwordText)


                    viewModel.signUpUser(emailText.toString(), passwordText, fullNameText.toString())
                            .observe(this, {
                                when (it.status) {
                                    Status.SUCCESS -> {
                                        binding.pbSignUp.visibility = View.GONE
                                        viewModel.saveUser2(user)
                                        binding.root.rootView.showsnackBar("User account registered")
                                        toSignIn()
                                    }
                                    Status.ERROR -> {
                                        binding.pbSignUp.visibility = View.GONE
                                        binding.root.rootView.showsnackBar(it.message!!)
                                    }
                                    Status.LOADING -> {
                                        binding.pbSignUp.visibility = View.VISIBLE
                                    }
                                }
                            })

                }

            }
        }


    }


    private fun toSignIn() {
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}


fun View.showsnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}




