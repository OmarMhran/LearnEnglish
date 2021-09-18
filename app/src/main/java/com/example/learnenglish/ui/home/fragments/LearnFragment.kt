package com.example.learnenglish.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learnenglish.R
import com.example.learnenglish.data.AppPreference
import com.example.learnenglish.data.Status
import com.example.learnenglish.ui.home.HomeActivity
import com.example.learnenglish.ui.home.HomeViewModel
import com.example.learnenglish.ui.home.adapter.LessonsAdapter
import com.example.learnenglish.ui.home.showsnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject


@AndroidEntryPoint
class LearnFragment : Fragment() {

    @Inject
    lateinit var appPreference: AppPreference
    lateinit var lessonsAdapter: LessonsAdapter
    lateinit var viewModel : HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerview()

        viewModel =  ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        appPreference.userScoreFlow.asLiveData().observe(viewLifecycleOwner, Observer { name ->
            if (name.isNotEmpty()){
                tvLevel.text = "Lv: $name"
            }
        })

        lifecycleScope.launch {
            viewModel.getLessons()
            viewModel.lessonsLiveData.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        pbLearn.visibility = View.VISIBLE
                    }

                    Status.SUCCESS -> {
                       lessonsAdapter.differ.submitList(it.data!!)
                        pbLearn.visibility = View.GONE
                    }

                    Status.ERROR -> {
                        pbLearn.visibility = View.GONE
                        view.showsnackBar(it.message!!)
                    }
                }
            })
        }



        btnToTest.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_learnFragment_to_homeFragment)
        }

        lessonsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("lesson",it)
            }
            Navigation.findNavController(view).navigate(R.id.action_learnFragment_to_lessonFragment,bundle)

        }
    }

    private fun setUpRecyclerview() {
        lessonsAdapter = LessonsAdapter()
        rvLessons.apply {
            adapter = lessonsAdapter
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
    }

}