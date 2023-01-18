package com.arnold.tic_tac_toewithai.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arnold.tic_tac_toewithai.R
import com.arnold.tic_tac_toewithai.databinding.FragmentShopBinding


class ShopFragment : Fragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

}