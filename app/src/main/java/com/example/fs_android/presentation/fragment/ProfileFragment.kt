package com.example.fs_android.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fs_android.MainActivity
import com.example.fs_android.databinding.FragmentProfileBinding
import com.example.fs_android.domain.model.user.User
import com.example.fs_android.presentation.LoginActivity
import com.example.fs_android.utils.DataStoreUtils
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnLogout?.setOnClickListener {
            activity?.let {
                doLogOut(it as MainActivity)
            }
        }
    }

    private fun doLogOut(activity: MainActivity) {
        lifecycleScope.launch {
            DataStoreUtils.get().updateData {
                User()
            }
        }
        val i = Intent(activity, LoginActivity::class.java)
        startActivity(i)
        activity.finishAffinity()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}