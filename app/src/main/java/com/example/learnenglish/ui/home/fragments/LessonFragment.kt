package com.example.learnenglish.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.learnenglish.R

import kotlinx.android.synthetic.main.fragment_lesson.*

class LessonFragment : Fragment() {

    val args: LessonFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lesson =  args.lesson

        wvLesson.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(lesson.lecture.toString())
        }
    }

}