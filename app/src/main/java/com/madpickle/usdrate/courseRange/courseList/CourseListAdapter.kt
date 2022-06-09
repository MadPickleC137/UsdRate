package com.madpickle.usdrate.courseRange.courseList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madpickle.usdrate.R
import com.madpickle.usdrate.data.CourseRange
import com.madpickle.usdrate.databinding.ItemCourseRangeBinding

/**
 * Created by David Madilyan on 06.06.2022.
 */
class CourseListAdapter(val context: Context): RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {

    private val items = mutableListOf<CourseRange>()

    inner class CourseListViewHolder(viewGroup: ViewGroup, val binding: ItemCourseRangeBinding
    = ItemCourseRangeBinding.inflate(LayoutInflater.from(viewGroup.context),
            viewGroup,
            false)):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder =
        CourseListViewHolder(parent)

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        val item = items[position]
        holder.binding.date.text = String.format(context.getString(R.string.item_course_date), item.date ?: "")
        holder.binding.nominal.text = String.format(context.getString(R.string.item_course_nominal), item.nominal ?: "")
        holder.binding.value.text = String.format(context.getString(R.string.item_course_value), item.value ?: "")
    }

    override fun getItemCount(): Int = items.size

    private fun clearAll(){
        notifyItemRangeRemoved(0, itemCount)
        items.clear()
    }

    fun updateList(newList: List<CourseRange>){
        if(newList != items){
            clearAll()
            items.addAll(newList)
            notifyItemRangeInserted(0, itemCount)
        }
    }
}