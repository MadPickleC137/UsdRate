package com.madpickle.usdrate.dailyCourses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.flexbox.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.madpickle.usdrate.R
import com.madpickle.usdrate.databinding.FragmentDialyCoursesBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент со списком валют за текущий день по умолчанию
 */
@AndroidEntryPoint
class DailyCoursesFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = DailyCoursesFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    private val binding: FragmentDialyCoursesBinding by
        viewBinding(FragmentDialyCoursesBinding::bind)

    private val viewModel: DailyCoursesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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
        binding.toolbar.menu.findItem(R.id.add_alarm_item).isVisible = false

        binding.recyclerDailyCourses.adapter = DailyCoursesAdapter(requireContext()) {
            viewModel.onSelectCourse(it)
        }

        binding.recyclerDailyCourses.layoutManager = FlexboxLayoutManager(requireContext()).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }

    }

    private fun showDisconnectMessage(){
        val snackBar = Snackbar.make(binding.snackBarContent,
            getString(R.string.disconnect_description),
            Snackbar.LENGTH_LONG)
        snackBar.setActionTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBar.show()
    }

}