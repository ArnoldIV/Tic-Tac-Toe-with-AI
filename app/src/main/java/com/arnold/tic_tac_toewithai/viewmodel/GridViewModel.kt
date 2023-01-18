package com.arnold.tic_tac_toewithai.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnold.tic_tac_toewithai.DataStoreRepository
import com.arnold.tic_tac_toewithai.models.Cells
import com.arnold.tic_tac_toewithai.models.CellsState
import com.arnold.tic_tac_toewithai.models.Grid
import com.arnold.tic_tac_toewithai.models.GridState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GridViewModel : ViewModel() {
    //(val dataStoreRepository: DataStoreRepository)
    private val mainGrid = Grid()
    var amountOfMoney = 0
     val grid = MutableLiveData(mainGrid)
    val money = MutableLiveData(amountOfMoney)

    //val readAmountOfMoney = dataStoreRepository.readAmountOfMoney

   // fun saveAmoutOfMoney(money: Int) = viewModelScope.launch(Dispatchers.IO){
        //dataStoreRepository.saveAmountOfMoney(money)
   // }

    private fun updateBoard() {
        grid.value = mainGrid
        money.value = amountOfMoney
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
        mainGrid.resetGrid()
        updateBoard()
    }
}
