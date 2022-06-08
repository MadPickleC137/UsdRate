package com.madpickle.usdrate.dailyCourses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.SyncResult
import com.madpickle.usdrate.core.extensions.observe
import com.madpickle.usdrate.core.extensions.safeNavigate
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.databinding.FragmentDialyCoursesBinding
import com.madpickle.usdrate.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент со списком валют за текущий день по умолчанию
 */
@AndroidEntryPoint
class DailyCoursesFragment : Fragment() {

    private var _binding: FragmentDialyCoursesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DailyCoursesViewModel by viewModels()
    private var adapter: DailyCoursesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialyCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.toolbar.menu.findItem(R.id.add_alarm_item).isVisible = false
        adapter = DailyCoursesAdapter(requireContext()) { courseDay ->
            findNavController().safeNavigate(
                R.id.action_dailyCoursesFragment_to_courseRangeFragment,
                CourseDay.toBundle(courseDay)
            )
        }
        binding.recyclerDailyCourses.adapter = adapter
        binding.recyclerDailyCourses.layoutManager = FlexboxLayoutManager(requireContext()).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
    }

    private fun initObservers() {
        observe(viewModel.syncResult){ result ->
              result?.let { updateState(result) }
        }
        observe(viewModel.listCourseDay) { list ->
            list?.let { adapter?.updateItems(list) }
        }
        observe(viewModel.selectedDay){ selectedDay ->
            selectedDay?.let {
                binding.toolbar.subtitle = String.format(getString(R.string.daily_courses_subtitle), selectedDay)
            }
        }
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
        snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBar.show()
    }
}