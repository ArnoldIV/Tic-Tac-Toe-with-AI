package com.arnold.tic_tac_toewithai.models

class Grid ( private val gameGrid:MutableMap<Cells, CellsState> = mutableMapOf()) {
    fun getState(cell:Cells): CellsState{
        return gameGrid[cell] ?: CellsState.Empty
    }

    fun setCell(cell: Cells, state: CellsState): Boolean{
        if (gameGrid.containsKey(cell)){
            return false
        }
        gameGrid[cell] = state
        return true
    }

    fun resetGrid(){
        gameGrid.clear()
    }

    fun findNextWinningCell(state: CellsState): Cells? = when{
        Cells.TOP_LEFT winsFor state -> Cells.TOP_LEFT
        Cells.TOP_CENTER winsFor state -> Cells.TOP_CENTER
        Cells.TOP_RIGHT winsFor state -> Cells.TOP_RIGHT
        Cells.CENTER_LEFT winsFor state -> Cells.CENTER_LEFT
        Cells.CENTER_CENTER winsFor state -> Cells.CENTER_CENTER
        Cells.CENTER_RIGHT winsFor state -> Cells.CENTER_RIGHT
        Cells.BOTTOM_LEFT winsFor state -> Cells.BOTTOM_LEFT
        Cells.BOTTOM_CENTER winsFor state -> Cells.BOTTOM_CENTER
        Cells.BOTTOM_RIGHT winsFor state -> Cells.BOTTOM_RIGHT

        else -> null
    }

    private infix fun Cells.winsFor(state: CellsState): Boolean{
        if (gameGrid.containsKey(this)){
            return false
        }
        gameGrid[this] = state
        val hasWon = hasStateWon(state)
        gameGrid.remove(this)
        return hasWon
    }

    val gridState: GridState
    get() = when{
        hasStateWon(CellsState.Player) -> GridState.PLAYER_WIN
        hasStateWon(CellsState.AI) -> GridState.AI_WIN
        gameGrid.size < 9 -> GridState.INCOMPLETE
        else -> GridState.DRAW
    }

    private fun hasStateWon(state: CellsState): Boolean{
        fun testState(vararg cells: Cells): Boolean = cells.all { cell->
        gameGrid[cell] == state
        }
        return testState(Cells.TOP_LEFT, Cells.CENTER_LEFT, Cells.BOTTOM_LEFT) ||
                testState(Cells.TOP_CENTER,Cells.CENTER_CENTER, Cells.BOTTOM_CENTER) ||
                testState(Cells.TOP_RIGHT,Cells.CENTER_RIGHT, Cells.BOTTOM_RIGHT) ||
                testState(Cells.TOP_LEFT,Cells.CENTER_CENTER, Cells.BOTTOM_RIGHT) ||
                testState(Cells.TOP_RIGHT,Cells.CENTER_CENTER, Cells.BOTTOM_LEFT) ||
                testState(Cells.TOP_LEFT,Cells.TOP_CENTER, Cells.TOP_RIGHT) ||
                testState(Cells.CENTER_LEFT,Cells.CENTER_CENTER, Cells.CENTER_RIGHT) ||
                testState(Cells.BOTTOM_LEFT,Cells.BOTTOM_CENTER, Cells.BOTTOM_RIGHT)

    }

}