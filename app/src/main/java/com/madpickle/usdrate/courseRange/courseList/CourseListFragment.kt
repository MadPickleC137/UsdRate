package com.madpickle.usdrate.courseRange.courseList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.madpickle.usdrate.core.extensions.observe
import com.madpickle.usdrate.courseRange.CourseRangeViewModel
import com.madpickle.usdrate.databinding.FragmentCourseListBinding

/**
 * Отображает список с курсом валюты по выбранным датам
 * */
class CourseListFragment : Fragment() {

    //получение родительской viewModel из CourseRangeFragment
    private val viewModel: CourseRangeViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CourseRangeViewModel::class.java]
    }

    private var _binding: FragmentCourseListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {

    }

    private fun initViews() {
        binding.courseRecyclerView.adapter = CourseListAdapter(requireContext()).apply {
            observe(viewModel.listCourse){ list ->
                list?.let {
                    updateList(list)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}