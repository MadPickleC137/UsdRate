package com.madpickle.usdrate.dailyCourses

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.databinding.ItemDailyCourseBinding

/**
 * Created by David Madilyan on 05.06.2022.
 */
class DailyCoursesAdapter(val context: Context, val onItemClick: (CourseDay) -> Unit):
    RecyclerView.Adapter<DailyCoursesAdapter.DailyCoursesViewHolder>() {
    private val items = mutableListOf<CourseDay>()

    inner class DailyCoursesViewHolder(viewGroup: ViewGroup, val binding: ItemDailyCourseBinding
    = ItemDailyCourseBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyCoursesViewHolder =
        DailyCoursesViewHolder(parent)

    override fun onBindViewHolder(holder: DailyCoursesViewHolder, position: Int) {
        val item = items[position]

        holder.binding.charCode.text = item.charCode ?: ""
        holder.binding.name.text = item.name ?: ""
        holder.binding.value.text = (item.value ?: 0.0).toString()
        holder.binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
    }

    override fun getItemCount() = items.size
}