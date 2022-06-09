package com.madpickle.usdrate.courseRange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.madpickle.usdrate.R
import com.madpickle.usdrate.calendar.showRangeDatePicker
import com.madpickle.usdrate.core.SyncResult
import com.madpickle.usdrate.core.extensions.observe
import com.madpickle.usdrate.core.extensions.safeNavigate
import com.madpickle.usdrate.courseRange.courseChart.CourseChartFragment
import com.madpickle.usdrate.courseRange.courseList.CourseListFragment
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.data.CourseDay.Companion.ARG_COURSE_DAY
import com.madpickle.usdrate.databinding.FragmentCourseRangeBinding
import com.madpickle.usdrate.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент родительский для [CourseListFragment] и [CourseChartFragment]
 * */
@AndroidEntryPoint
class CourseRangeFragment : Fragment() {

    private val viewModel: CourseRangeViewModel by viewModels()
    private var _binding: FragmentCourseRangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseRangeBinding.inflate(inflater, container, false)
            .apply {
                arguments?.getParcelable<CourseDay>(ARG_COURSE_DAY)
                    ?.let { viewModel.setCourseDay(it) }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.menu.findItem(R.id.add_alarm_item).isVisible = true
        binding.viewPager.adapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout,  binding.viewPager) { tab, position ->
            if (position == 0)
                tab.text = getString(R.string.course_list)
            else
                tab.text = getString(R.string.course_chart)
        }.attach()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.calendar_item ->{
                    showCalendar()
                }
                R.id.add_alarm_item ->{
                    viewModel.getCourseDay()?.let { courseDay ->
                        findNavController().safeNavigate(
                            R.id.action_courseRangeFragment_to_createNotifyFragment,
                            CourseDay.toBundle(courseDay)
                        )
                    }
                }
            }
            return@setOnMenuItemClickListener true
        }
        initObservers()
    }

    private fun showCalendar() {
        showRangeDatePicker(requireContext(), childFragmentManager){ start, end ->
            viewModel.setDateRange(start, end)
        }
    }

    private fun initObservers() {
        observe(viewModel.title){ title ->
            title?.let { binding.toolbar.title = it }
        }
        observe(viewModel.subTitle){ subTitle ->
            subTitle?.let { binding.toolbar.subtitle = it }
        }
        observe(viewModel.resultState){ result ->
            result?.let { updateState(result) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateState(syncResult: SyncResult) = when(syncResult){
        SyncResult.SUCCESS -> (activity as? MainActivity)?.setProgressVisible(false)
        SyncResult.LOADING -> (activity as? MainActivity)?.setProgressVisible(true)
        SyncResult.ERROR -> showDisconnectMessage()
        SyncResult.EMPTY -> showEmptyContent()
    }

    private fun showEmptyContent() {
        (activity as? MainActivity)?.setProgressVisible(false)
        binding.layoutEmptyData.root.isVisible = true
    }

    private fun showDisconnectMessage(){
        (activity as? MainActivity)?.setProgressVisible(false)
        val snackBar = Snackbar.make(binding.snackBarContent,
            getString(R.string.disconnect_description),
            Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackBar.show()
    }

    /**
     * Адаптер страниц содержимого закладок.
     */
    private class PagerAdapter(fm: FragmentManager, lifecycle: Lifecycle):
        FragmentStateAdapter(fm, lifecycle) {
        val screens = listOf(CourseListFragment(), CourseChartFragment())
        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            return screens[position]
        }
    }
}