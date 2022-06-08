package com.madpickle.usdrate.courseRange.courseChart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartZoomType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.extensions.observe
import com.madpickle.usdrate.courseRange.CourseRangeViewModel
import com.madpickle.usdrate.databinding.FragmentCourseChartBinding


/**
 * Отображает график с курсом валюты
 */
class CourseChartFragment : Fragment() {

    private var _binding: FragmentCourseChartBinding? = null
    private val binding get() = _binding!!


    //получение родительской viewModel из CourseRangeFragment
    private val vm: CourseRangeViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CourseRangeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentCourseChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Spline)
            .dataLabelsEnabled(true)
            .zoomType(AAChartZoomType.X)
            .xAxisVisible(false)
            .yAxisTitle(getString(R.string.chart_yaxis_title))

        observe(vm.chartSeries){ series ->
            series?.let {
                aaChartModel.series(arrayOf(
                    AASeriesElement()
                        .color("#71298C")
                        .tooltip(AATooltip().enabled(false))
                        .name(getString(R.string.chart_series_title))
                        .data(it.toTypedArray()),
                ))
                binding.courseChartView.aa_drawChartWithChartModel(aaChartModel)
            }
        }
    }


}