package com.madpickle.usdrate.courseRange.courseChart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.madpickle.usdrate.R
import com.madpickle.usdrate.courseRange.CourseRangeViewModel


/**
 * Отображает график с курсом валюты
 */
class CourseChartFragment : Fragment() {

    //получение родительской viewModel из CourseRangeFragment
    private val vm: CourseRangeViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CourseRangeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_chart, container, false)
    }
}