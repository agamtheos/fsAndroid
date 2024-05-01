//package com.example.fs_android.presentation.fragment
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
//import com.example.fs_android.base.AppModule
//import com.example.fs_android.databinding.FragmentFirstBinding
//import com.example.fs_android.domain.model.batch.Batch
//import com.example.fs_android.presentation.adapter.ChartAdapter
//import com.example.fs_android.presentation.viewmodel.ChartViewModel
//import com.example.fs_android.utils.Error
//import com.example.fs_android.utils.Initiate
//import com.example.fs_android.utils.Loading
//import com.example.fs_android.utils.Success
//import com.example.fs_android.utils.observeIn
//
//// chart fragment is sigle data fragment
//class ChartFragment : Fragment() {
//    private val chartViewModel by viewModels<ChartViewModel> { AppModule.chartViewModelFactory }
//
//    private var _binding: FragmentFirstBinding? = null
//    private val binding get() = _binding
//
//    private var adapter: ChartAdapter? = null
//
//    private var id = "1"
//
//    companion object {
//        @JvmStatic
//        fun newInstance() = ChartFragment()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): SwipeRefreshLayout? {
//        _binding = FragmentFirstBinding.inflate(inflater, container, false)
//        return binding?.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        observer(view.context)
//        getDetail(id)
//    }
//
//    private fun getDetail(id: String) {
//        chartViewModel.getBatch(id)
//    }
//
////    private fun initListener() {
////        // create on click batch listener, and move to ChartActivity
////        adapter?.setOnBatchClickListener {
////            // move to ChartActivity
////        }
////    }
//
//    private fun observer(context: Context?) {
//        chartViewModel.batch.observeIn(this) {
//            when (it) {
//                is Success -> {
//                    showBatch(it.data)
//                }
//
//                is Error -> {
//                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                }
//
//                is Initiate -> {
//
//                }
//
//                is Loading -> {
//                    showLoader(it.isLoading)
//                }
//            }
//        }
//    }
//
//    private fun loadItem() {
//        chartViewModel.getBatch(id = id)
//    }
//
//    private fun showLoader(isLoading: Boolean) {
//        binding?.apply {
//            pbHome.isVisible = isLoading
//            chart.isVisible = !isLoading
//        }
//    }
//
//    private fun showBatch(data: Batch) {
//        adapter?.add(data)
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//
//}