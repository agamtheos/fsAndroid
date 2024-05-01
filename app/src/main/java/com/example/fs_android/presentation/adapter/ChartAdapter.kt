//package com.example.fs_android.presentation.adapter
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.fs_android.databinding.ContentChartBinding
//import com.example.fs_android.domain.model.batch.Batch
//
//
//// Chart is not list but single item
//class ChartAdapter(
//    private val context: Context,
//    private var item: Batch
//) : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ChartAdapter.ChartViewHolder {
//        val binding =
//            ContentChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ChartViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ChartAdapter.ChartViewHolder, position: Int) {
//        holder.bind(item)
//    }
//
//    override fun getItemCount(): Int {
//        return 1
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun add(data: Batch) {
//        item = data
//        notifyDataSetChanged()
//    }
//
//    inner class ChartViewHolder(private val binding: ContentChartBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: Batch) {
//            with(binding) {
//                textTitle.text = data.batchName
//                textCreatedAt.text = data.createdAt.toString()
//                textCreatedBy.text = data.createdBy.toString()
//                textStatus.text = data.status
//            }
//        }
//    }
//
//    fun item(): Batch {
//        return item
//    }
//
////    fun clear() {
////        item = Batch()
////        notifyDataSetChanged()
////    }
//}