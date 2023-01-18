package com.arnold.tic_tac_toewithai.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColor
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.arnold.tic_tac_toewithai.R
import com.arnold.tic_tac_toewithai.databinding.FragmentShopBinding
import com.arnold.tic_tac_toewithai.models.CellsState
import com.arnold.tic_tac_toewithai.viewmodel.GridViewModel
import javax.inject.Inject


class ShopFragment: Fragment(R.layout.fragment_shop) {

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

            binding.buyRedColor.setOnClickListener {
                //TODO(реалізувати зміну кольору ігрових фігур при натисканні кнопки)
                // Ігрові фігури знаходяться у CellsState
                      viewModel.readAmountOfMoney.asLiveData().observe(viewLifecycleOwner){
                          value -> amountOfMoney = value.money
                          if (amountOfMoney > 10){
                          amountOfMoney -= 10
                      }else {
                              Toast.makeText(
                                  requireContext(),
                                  "Earn more money",
                                  Toast.LENGTH_SHORT
                              ).show()
                          }
                      }
                viewModel.saveAmountOfMoney(amountOfMoney)

                }


            }


        return binding.root
    }
}