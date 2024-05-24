package com.example.learnenglish.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.learnenglish.R
import com.example.learnenglish.databinding.ActivityMainBinding
import com.example.learnenglish.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration : AppBarConfiguration
    lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent?.getStringExtra("username").toString()
        val email = intent?.getStringExtra("email").toString()

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setSupportActionBar(binding.toolbar)
        binding.tbTitle.text = binding.toolbar.title
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        drawerLayout = binding.drawerLayout


        val navController = findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.homeFragment, R.id.scoreFragment, R.id.learnFragment, R.id.lessonFragment
        ), drawerLayout)

        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }

        }




        val logoutItem = binding.navView.menu.findItem(R.id.logout)
        logoutItem.setOnMenuItemClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                toLoginActivity()
            }
             true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun toLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        this.finish()
    }


}