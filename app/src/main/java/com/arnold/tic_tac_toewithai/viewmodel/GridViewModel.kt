package com.arnold.tic_tac_toewithai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arnold.tic_tac_toewithai.AmountOfMoney
import com.arnold.tic_tac_toewithai.DataStoreRepository
import com.arnold.tic_tac_toewithai.models.Cells
import com.arnold.tic_tac_toewithai.models.CellsState
import com.arnold.tic_tac_toewithai.models.Grid
import com.arnold.tic_tac_toewithai.models.GridState
import com.arnold.tic_tac_toewithai.util.Constants.Companion.DEFAULT_AMOUNT_OF_MONEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository)
    : AndroidViewModel(application) {

    private val mainGrid = Grid()
    private var amountOfMoney = 0
    val grid = MutableLiveData(mainGrid)

     val readAmountOfMoney = dataStoreRepository.readAmountOfMoney





    fun saveAmountOfMoney(amountOfMoney: Int) = viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.saveAmountOfMoney(amountOfMoney)
    }



    private fun updateBoard() {
        grid.value = mainGrid

    }

    fun cellClicked(cell: Cells) {
        if (mainGrid.setCell(cell, CellsState.Player)) {
            updateBoard()
            if (mainGrid.gridState == GridState.INCOMPLETE) {
                aiTurn()
            }
        }
    }

    private fun aiTurn() {
        val aiWinningCell = mainGrid.findNextWinningCell(CellsState.AI)
        val playerWinningCell = mainGrid.findNextWinningCell(CellsState.Player)
        when {
            // If the AI can win, place a circle in that spot
            aiWinningCell != null -> mainGrid.setCell(aiWinningCell, CellsState.AI)
            // If the AI is about to lose, place a circle in a blocking spot
            playerWinningCell != null -> mainGrid.setCell(playerWinningCell, CellsState.AI)
            // Prioritize the middle
            mainGrid.setCell(Cells.CENTER_CENTER, CellsState.AI) -> Unit
            // Otherwise place a circle in a random spot
            else -> do {
                val nextCell = Cells.values().random()
                val placeSuccess = mainGrid.setCell(nextCell, CellsState.AI)
            } while (!placeSuccess)
        }

        updateBoard()
    }

    fun resetGrid() {
        //saveAmountOfMoney(amountOfMoney)
        mainGrid.resetGrid()
        updateBoard()
    }
}
