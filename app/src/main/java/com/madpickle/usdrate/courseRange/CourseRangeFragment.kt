package com.madpickle.usdrate.courseRange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.madpickle.usdrate.R
import com.madpickle.usdrate.courseRange.courseChart.CourseChartFragment
import com.madpickle.usdrate.courseRange.courseList.CourseListFragment
import com.madpickle.usdrate.databinding.FragmentCourseRangeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент родительский для [CourseListFragment] и [CourseChartFragment]
 * */
@AndroidEntryPoint
class CourseRangeFragment : Fragment() {

    private val viewModel: CourseRangeViewModel by viewModels()
    private val binding: FragmentCourseRangeBinding by viewBinding(FragmentCourseRangeBinding::bind)

    companion object {
        fun newInstance() = CourseRangeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = PagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout,  binding.viewPager) { tab, position ->
            if (position == 0)
                tab.text = getString(R.string.course_list)
            else
                tab.text = getString(R.string.course_chart)
        }.attach()


    }


    /**
     * Адаптер страниц содержимого закладок.
     */
    private class PagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
        val screens = listOf(CourseListFragment(), CourseChartFragment())
        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            return screens[position]
        }
    }
}