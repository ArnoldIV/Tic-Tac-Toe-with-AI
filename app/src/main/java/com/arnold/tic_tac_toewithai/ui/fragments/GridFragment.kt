package com.arnold.tic_tac_toewithai.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arnold.tic_tac_toewithai.viewmodel.GridViewModel
import com.arnold.tic_tac_toewithai.R
import com.arnold.tic_tac_toewithai.databinding.FragmentGridBinding
import com.arnold.tic_tac_toewithai.models.Grid
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.arnold.tic_tac_toewithai.models.Cells
import com.arnold.tic_tac_toewithai.models.GridState


open class GridFragment : Fragment(R.layout.fragment_grid) {

    private var _binding: FragmentGridBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GridViewModel
    var amountOfMoney = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GridViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGridBinding.inflate(inflater, container, false)
        viewModel.grid.observe(viewLifecycleOwner, onGridChange)


        viewModel.readAmountOfMoney.asLiveData().observe(viewLifecycleOwner){value ->
            amountOfMoney = value.money
            binding.amountOfMoneyTextView.text = value.money.toString()
            Log.d("valuemoney", amountOfMoney.toString())
        }

        bindButtons()
        return binding.root
    }

    private val onGridChange = Observer { grid: Grid ->
        updateGridCell(grid)
        updateGameResult(grid.gridState)
    }

    private fun updateGridCell(grid: Grid) {
        binding.square0.setImageResource(grid.getState(Cells.TOP_LEFT).res)
        binding.square1.setImageResource(grid.getState(Cells.TOP_CENTER).res)
        binding.square2.setImageResource(grid.getState(Cells.TOP_RIGHT).res)
        binding.square3.setImageResource(grid.getState(Cells.CENTER_LEFT).res)
        binding.square4.setImageResource(grid.getState(Cells.CENTER_CENTER).res)
        binding.square5.setImageResource(grid.getState(Cells.CENTER_RIGHT).res)
        binding.square6.setImageResource(grid.getState(Cells.BOTTOM_LEFT).res)
        binding.square7.setImageResource(grid.getState(Cells.BOTTOM_CENTER).res)
        binding.square8.setImageResource(grid.getState(Cells.BOTTOM_RIGHT).res)
    }


    private fun updateGameResult(gridState: GridState) = when (gridState) {
        GridState.AI_WIN -> {
            setupBoard(true)
            binding.gameResultTextView.visibility = View.VISIBLE
            binding.gameResultTextView.setText(R.string.ai_win_message)
            amountOfMoney += 0
        }
        GridState.PLAYER_WIN -> {
            setupBoard(true)
            amountOfMoney += 10
            viewModel.saveAmountOfMoney(amountOfMoney)
            binding.gameResultTextView.visibility = View.VISIBLE
            binding.gameResultTextView.setText(R.string.player_win_message)

        }
        GridState.DRAW -> {
            setupBoard(true)
            amountOfMoney += 2
            viewModel.saveAmountOfMoney(amountOfMoney)
            binding.gameResultTextView.visibility = View.VISIBLE
            binding.gameResultTextView.setText(R.string.draw_message)
        }
        GridState.INCOMPLETE -> {
            setupBoard(false)
            binding.gameResultTextView.visibility = View.GONE
        }
    }

    private fun setupBoard(disable: Boolean = false) {
        binding.square0.isEnabled = !disable
        binding.square1.isEnabled = !disable
        binding.square2.isEnabled = !disable
        binding.square3.isEnabled = !disable
        binding.square4.isEnabled = !disable
        binding.square5.isEnabled = !disable
        binding.square6.isEnabled = !disable
        binding.square7.isEnabled = !disable
        binding.square8.isEnabled = !disable

        binding.square0.alpha = if (disable) 0.5f else 1f
        binding.square1.alpha = if (disable) 0.5f else 1f
        binding.square2.alpha = if (disable) 0.5f else 1f
        binding.square3.alpha = if (disable) 0.5f else 1f
        binding.square4.alpha = if (disable) 0.5f else 1f
        binding.square5.alpha = if (disable) 0.5f else 1f
        binding.square6.alpha = if (disable) 0.5f else 1f
        binding.square7.alpha = if (disable) 0.5f else 1f
        binding.square8.alpha = if (disable) 0.5f else 1f
    }

    fun bindButtons() {
        with(binding) {
            buttonReset.setOnClickListener {
                viewModel.resetGrid() }
            square0.setOnClickListener { viewModel.cellClicked(Cells.TOP_LEFT) }
            square1.setOnClickListener { viewModel.cellClicked(Cells.TOP_CENTER) }
            square2.setOnClickListener { viewModel.cellClicked(Cells.TOP_RIGHT) }
            square3.setOnClickListener { viewModel.cellClicked(Cells.CENTER_LEFT) }
            square4.setOnClickListener { viewModel.cellClicked(Cells.CENTER_CENTER) }
            square5.setOnClickListener { viewModel.cellClicked(Cells.CENTER_RIGHT) }
            square6.setOnClickListener { viewModel.cellClicked(Cells.BOTTOM_LEFT) }
            square7.setOnClickListener { viewModel.cellClicked(Cells.BOTTOM_CENTER) }
            square8.setOnClickListener { viewModel.cellClicked(Cells.BOTTOM_RIGHT) }
        }

    }

    @SuppressLint("ResourceAsColor")
    fun changeColorToRed(){
        binding.square0.setBackgroundColor(R.color.red)
        binding.square1.setBackgroundColor(R.color.red)
        binding.square2.setBackgroundColor(R.color.red)
        binding.square3.setBackgroundColor(R.color.red)
        binding.square4.setBackgroundColor(R.color.red)
        binding.square5.setBackgroundColor(R.color.red)
        binding.square6.setBackgroundColor(R.color.red)
        binding.square7.setBackgroundColor(R.color.red)
        binding.square8.setBackgroundColor(R.color.red)
    }

}