package com.example.fs_android.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fs_android.databinding.ItemBatchBinding
import com.example.fs_android.domain.model.batch.Batch

class BatchAdapter(
    private val contex: Context,
    private val items: MutableList<Batch>,
    private val batchClickListener: BatchClickListener
) : RecyclerView.Adapter<BatchAdapter.BatchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BatchAdapter.BatchViewHolder {
        val binding = ItemBatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BatchAdapter.BatchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(data: List<Batch>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class BatchViewHolder(private val binding: ItemBatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Batch) {
            with(binding) {
                textTitle.text = data.batchName
                textCreatedAt.text = data.createdAt.toString()
                textCreatedBy.text = data.createdBy.toString()
                textStatus.text = data.status
            }
        }

        init {
            binding.root.setOnClickListener {
                batchClickListener.onBatchClicked(items[adapterPosition].funderStatementBatchId)
            }
        }
    }

    fun items(): MutableList<Batch> {
        return items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    interface BatchClickListener {
        fun onBatchClicked(batchId: String)
    }
}