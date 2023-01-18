package com.arnold.tic_tac_toewithai.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.arnold.tic_tac_toewithai.R
import com.arnold.tic_tac_toewithai.databinding.FragmentShopBinding
import com.arnold.tic_tac_toewithai.viewmodel.GridViewModel


class ShopFragment : Fragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: GridViewModel
    var amountOfMoney = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShopBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(GridViewModel::class.java)

        viewModel.readAmountOfMoney.asLiveData().observe(viewLifecycleOwner) { value ->
            amountOfMoney = value.money
            binding.amountOfMoneyTextView.text = value.money.toString()

            binding.buyRedColor.setOnClickListener{

            }



        }
        return binding.root
    }

    fun setColor(){
        R.drawable.ic_x.toColor().red()
    }

}