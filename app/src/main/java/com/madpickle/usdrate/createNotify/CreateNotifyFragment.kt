package com.madpickle.usdrate.createNotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.ValidationFields
import com.madpickle.usdrate.core.extensions.observe
import com.madpickle.usdrate.core.utils.EventObserver
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.databinding.FragmentCreateNotifyBinding
import dagger.hilt.android.AndroidEntryPoint
import id.ionbit.ionalert.IonAlert


/**
 * Фрагмент отвечающий за создание параметров для нотификации
 */
@AndroidEntryPoint
class CreateNotifyFragment : Fragment() {

    private var _binding: FragmentCreateNotifyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateNotifyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getParcelable<CourseDay>(CourseDay.ARG_COURSE_DAY)
                ?.let { course -> viewModel.setCourseDay(course) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNotifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.createNotifyButton.setOnClickListener {
            viewModel.setNotification()
        }

        binding.maxValueEdit.doAfterTextChanged {
            if(!it.isNullOrEmpty()){
                viewModel.setMaxValue(it.toString().toDouble())
            }
        }

        binding.minValueEdit.doAfterTextChanged {
            if(!it.isNullOrEmpty()){
                viewModel.setMinValue(it.toString().toDouble())
            }
        }

        observe(viewModel.courseName) {
            binding.title.text = it
        }

        observe(viewModel.courseValue){
            binding.courseValue.text = it
        }
        viewModel.createWorkerEvent.observe(viewLifecycleOwner, EventObserver{
            showSuccessMessage()
        })
        observe(viewModel.validation){ validation ->
            validation?.let {
                val message = validationMessage(it)
                if(message != null){
                    val snackBar = Snackbar.make(binding.snackBarContent, message, Snackbar.LENGTH_LONG)
                    snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                    snackBar.show()
                }
            }
        }
    }

    private fun showSuccessMessage() {
        IonAlert(requireContext(), IonAlert.SUCCESS_TYPE)
            .setTitleText(getString(R.string.create_notify_success_title))
            .setContentText(getString(R.string.create_notify_success_desc))
            .setConfirmClickListener {
                it.cancel()
                findNavController().popBackStack()
            }
            .show()
    }

    private fun validationMessage(validation: ValidationFields): String? {
        return when(validation){
            ValidationFields.EMPTY -> getString(R.string.create_notify_require_values)
            ValidationFields.MISMATCH -> getString(R.string.create_notify_max_require)
            ValidationFields.SUCCESS -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}