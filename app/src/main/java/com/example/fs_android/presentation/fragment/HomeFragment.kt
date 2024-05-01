//package com.example.fs_android.presentation.fragment
//
//import HomeViewModel
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.fs_android.base.AppModule
//import com.example.fs_android.databinding.FragmentHomeBinding
////import com.example.fs_android.domain.model.Movie
//import com.example.fs_android.presentation.adapter.MovieAdapter
//import com.example.fs_android.utils.Error
//import com.example.fs_android.utils.Initiate
//import com.example.fs_android.utils.Loading
//import com.example.fs_android.utils.Success
//import com.example.fs_android.utils.observeIn
//
//class HomeFragment : Fragment() {
//
//    private val homeViewModel by viewModels<HomeViewModel> { AppModule.homeViewModelFactory }
//
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding
//
//    private var adapter: MovieAdapter? = null
//
//    private var page = 1
//    private var nextPage = 1
//
//    private val scrollListener = object : RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//            if (adapter == null) return
//            val totalItemCount = adapter?.itemCount ?: 0
//            val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
//            val lastIndex = adapter?.items()?.lastIndex ?: 0
//            if (totalItemCount <= 0) return
//            if (lastVisibleItem != lastIndex) return
//            if (page >= nextPage) return
//            loadItems()
//        }
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance() = HomeFragment()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding?.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        context?.let {
//            binding?.apply {
//                if (adapter == null) adapter = MovieAdapter(it, mutableListOf())
//                listMovie.apply {
//                    setHasFixedSize(true)
//                    layoutManager = LinearLayoutManager(context)
//                    adapter = this@HomeFragment.adapter
//                }
//
//                swpHome.setOnRefreshListener {
//                    swpHome.isRefreshing = false
//                    reset()
//                }
//            }
//
//            observer(view.context)
//
////            getListMovie()
//
//            initListener()
//        }
//
//    }
//
//    private fun getListMovie() {
//        homeViewModel.getMovie()
//    }
//
//
//    private fun initListener() {
//        binding?.listMovie?.apply {
//            removeOnScrollListener(scrollListener)
//            addOnScrollListener(scrollListener)
//        }
//    }
//
//    private fun loadItems() {
//        page += 1
//        homeViewModel.getMovie(page)
//    }
//
//    private fun observer(context: Context?) {
//        homeViewModel.movie.observeIn(this) {
//            when (it) {
//                is Success -> showListMovie(it.data)
//                is Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                is Loading -> showLoader(it.isLoading)
//                is Initiate -> {}
//            }
//        }
//    }
//
//    private fun reset() {
//        page = 1
//        nextPage = 1
//        adapter?.clear()
//        getListMovie()
//    }
//
//    private fun showLoader(isLoading: Boolean) {
//        binding?.apply {
//            pbHome.isVisible = isLoading
//            listMovie.isVisible = !isLoading
//        }
//    }
//
//    private fun showListMovie(data: List<Movie>) {
//        adapter?.addAll(data)
//        nextPage = page.plus(1)
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}