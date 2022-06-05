package com.madpickle.usdrate.courseRange.courseList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.madpickle.usdrate.R
import com.madpickle.usdrate.courseRange.CourseRangeViewModel

/**
 * Отображает список с курсом валюты по выбранным датам
 * */
class CourseListFragment : Fragment() {

    //получение родительской viewModel из CourseRangeFragment
    private val vm: CourseRangeViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CourseRangeViewModel::class.java]
    }

    companion object {
        fun newInstance() = CourseListFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_list, container, false)
    }

}