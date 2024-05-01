//package com.example.fs_android.presentation
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.anychart.AnyChart
//import com.anychart.chart.common.dataentry.DataEntry
//import com.example.fs_android.R
//import com.example.fs_android.databinding.ActivityChartBinding
//
//class ChartActivityOld : AppCompatActivity() {
//
//    private lateinit var binding: ActivityChartBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityChartBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        initializeToolbar()
//
//        // init Pie chart
//        val pie = AnyChart.pie()
//        // create arraylist for data
//        val data = List<DataEntry>(5)
//
//        // set data to pie chart
//        pie.data(data)
//
//    }
//
//    private fun initializeToolbar() {
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.apply {
//            title = getString(R.string.title_activity_chart)
//            setDisplayHomeAsUpEnabled(false)
//        }
//    }
//}