package com.example.learnenglish.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnenglish.R
import com.example.learnenglish.data.Status
import com.example.learnenglish.ui.home.adapter.QuestionAdapter
import com.example.learnenglish.ui.home.adapter.StepsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var stepsAdapter: StepsAdapter
    lateinit var questionsAdapter: QuestionAdapter
    lateinit var viewModel: HomeViewModel
    var stepCount = 0
    var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        viewModel =  ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        viewModel.examLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                   questionsAdapter.differ.submitList(it.data!!.parts[stepCount].questions)
                }

                Status.ERROR -> {
                    view.showsnackBar(it.message!!)
                }
            }
        })



        btnNext.setOnClickListener {
            score = questionsAdapter.correct
            if (questionsAdapter.nextQuiz()){
                stepCount += 1
                questionsAdapter.restSelectedCount()
                stepsAdapter.ifButtonNextClicked()
                rvQuestions.smoothScrollToPosition(0)

            }else{
                view.showsnackBar("Please answer all questions")
            }

            if (stepCount <= 3){
                viewModel.getNextPart(stepCount)
                viewModel.nextPartLiveData.observe(viewLifecycleOwner, Observer {
                    questionsAdapter.differ.submitList(it.questions)
                })
                score = questionsAdapter.correct
            }


            if (stepCount == 4){
                stepCount = 0
                navigateToScore(score)
            }

        }



    }

    private fun setUpRecyclerView() {
        stepsAdapter = StepsAdapter()
        rvSteps.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        rvSteps.adapter = stepsAdapter


        questionsAdapter = QuestionAdapter()
        rvQuestions.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        rvQuestions.adapter = questionsAdapter
    }

    override fun onPause() {
        super.onPause()
        stepCount = 0
    }

    fun navigateToScore(score: Int){
        val bundle = Bundle()
        bundle.putInt("score",score)
        findNavController().navigate(R.id.action_homeFragment_to_scoreFragment,bundle)
    }
}
fun View.showsnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}





