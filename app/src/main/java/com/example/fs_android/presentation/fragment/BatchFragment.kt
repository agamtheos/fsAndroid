package com.example.fs_android.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fs_android.ChartActivity
import com.example.fs_android.R
import com.example.fs_android.base.AppModule
import com.example.fs_android.databinding.FragmentBatchBinding
import com.example.fs_android.domain.model.batch.Batch
import com.example.fs_android.domain.model.user.User
import com.example.fs_android.presentation.adapter.BatchAdapter
import com.example.fs_android.presentation.viewmodel.BatchViewModel
import com.example.fs_android.utils.DataStoreUtils
import com.example.fs_android.utils.Error
import com.example.fs_android.utils.Initiate
import com.example.fs_android.utils.Loading
import com.example.fs_android.utils.Success
import com.example.fs_android.utils.observeIn
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date

class BatchFragment(private val dataStore: DataStore<User>?) : Fragment(),
    BatchAdapter.BatchClickListener {
    private val batchViewModel by viewModels<BatchViewModel> { AppModule.batchViewModelFactory }

    private var _binding: FragmentBatchBinding? = null
    private val binding get() = _binding

    private var adapter: BatchAdapter? = null

    private var page = 0
    private var nextPage = 0
    private var sort = "desc"
    private var sortBy = "createdAt"
    private var size = 5
    private var filterBy: String? = null
    private var filterValue: String? = null

    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            if (adapter == null) return
            val totalItemCount = adapter?.itemCount ?: 0
            val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
            val lastIndex = adapter?.items()?.lastIndex ?: 0
            if (totalItemCount <= 0) return
            if (lastVisibleItem != lastIndex) return
            if (page >= nextPage) return
            loadItems()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BatchFragment(DataStoreUtils.get())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val batchButton = view.findViewById<View>(R.id.llBatch)

        context?.let {
            binding?.apply {
                if (adapter == null) {
                    adapter = BatchAdapter(it, mutableListOf(), this@BatchFragment)
                }
                listBatch.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = this@BatchFragment.adapter
                }

                swpHome.setOnRefreshListener {
                    swpHome.isRefreshing = false
                    reset()
                }
            }

            observer(view.context)

            getListBatch(page, sort, sortBy, size)

            initListener()

            batchButton?.setOnClickListener {
                val intent = Intent(context, ChartActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getListBatch(
        page: Int,
        sort: String,
        sortBy: String,
        size: Int,
        filterBy: String? = null,
        filterValue: String? = null
    ) {
        batchViewModel.getBatch(
            page = page,
            sort = sort,
            sortBy = sortBy,
            size = size,
            filterBy = filterBy,
            filterValue = filterValue
        )
    }

    private fun initListener() {
        binding?.listBatch?.apply {
            removeOnScrollListener(scrollListener)
            addOnScrollListener(scrollListener)
        }
    }

    private fun loadItems() {
        page += 1
        batchViewModel.getBatch(
            page = page,
            sort = sort,
            sortBy = sortBy,
            size = size,
            filterBy = filterBy,
            filterValue = filterValue
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun observer(context: Context?) {
        batchViewModel.batch.observeIn(this) {
            when (it) {
                is Success -> {
                    // change createdAt in it.data to date format
                    it.data.forEach { batch ->
                        val date =
                            SimpleDateFormat("dd-MM-yyyy HH:mm").format(Date(batch.createdAt.toLong()))
                        batch.createdAt = "Dibuat Pada : $date"
                        batch.createdBy = "Dibuat Oleh : ${batch.createdBy}"
                    }

                    showListBatch(it.data)
//                    nextPage = it.data.size
                }

                is Error -> {
                    // handle error
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is Loading -> {
                    // handle loading
                    showLoader(it.isLoading)
                }

                is Initiate -> {
                    // handle initiate
                }
            }
        }
    }

    override fun onBatchClicked(batchId: String) {
        val intent = Intent(context, ChartActivity::class.java)
        intent.putExtra("batchId", batchId)
        val getToken = runBlocking {
            dataStore?.data?.firstOrNull()?.token // ini untuk nyimpan kaya localstorage
        }
        intent.putExtra("token", getToken)
        startActivity(intent)
    }

    private fun reset() {
        page = 0
        nextPage = 0
        adapter?.clear()
        getListBatch(page, sort, sortBy, size)
    }

    private fun showLoader(isLoading: Boolean) {
        binding?.apply {
            pbHome.isVisible = isLoading
            listBatch.isVisible = !isLoading
        }
    }

    private fun showListBatch(data: List<Batch>) {
        adapter?.addAll(data)
        nextPage = page.plus(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}