package com.example.fs_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fs_android.databinding.ActivityMainBinding
import com.example.fs_android.presentation.adapter.MainPagerAdapter
import com.example.fs_android.presentation.fragment.BatchFragment
import com.example.fs_android.presentation.fragment.ProfileFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeToolbar()
        initializeViewPager()
    }

    private fun initilizeChart() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(this)
//        adapter.addFragment(HomeFragment.newInstance())
        adapter.addFragment(BatchFragment.newInstance())
        adapter.addFragment(ProfileFragment.newInstance())
        with(binding) {
            vpMain.adapter = adapter
            TabLayoutMediator(tabMain, vpMain) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.label_report)
                    1 -> tab.text = getString(R.string.label_profile)
                }
            }.attach()
        }
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
        }
    }
}
