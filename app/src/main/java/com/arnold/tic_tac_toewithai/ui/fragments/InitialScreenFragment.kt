package com.arnold.tic_tac_toewithai.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arnold.tic_tac_toewithai.R
import com.arnold.tic_tac_toewithai.databinding.FragmentInitialScreenBinding


class InitialScreenFragment : Fragment() {

    private var _binding: FragmentInitialScreenBinding? = null
    private val binding get() = _binding!!
    var amountOfMoney = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInitialScreenBinding.inflate(inflater,
            container, false)

        binding.amountOfMoneyTextView.text = amountOfMoney.toString()

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_initialScreenFragment_to_fragment_grid)
        }

        binding.shopButton.setOnClickListener{
            findNavController().navigate(R.id.action_initialScreenFragment_to_shopFragment)
        }

        return binding.root
    }

}