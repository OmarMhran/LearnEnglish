package com.example.learnenglish.ui.home.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learnenglish.R
import com.example.learnenglish.ui.home.HomeActivity
import com.example.learnenglish.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.fragment_score.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScoreFragment : Fragment() {

    lateinit var viewModel : HomeViewModel
    private val args: ScoreFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val score = args.score
        Log.d("score",score.toString())

        viewModel =  ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        lifecycleScope.launch {
            viewModel.getScoreLevel(score)
            viewModel.scoreLiveData.observe(viewLifecycleOwner, Observer {
                lnScore.tvScoreLevel.text = it.toString()
            })
        }

        btntoLearn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_scoreFragment_to_learn_nav_graph)

        }




    }
}