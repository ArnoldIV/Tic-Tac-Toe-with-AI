package com.arnold.tic_tac_toewithai.models

import androidx.annotation.DrawableRes
import com.arnold.tic_tac_toewithai.R

sealed class CellsState (@DrawableRes val res: Int){
    object AI: CellsState(R.drawable.ic_o)
    object Player: CellsState(R.drawable.ic_x)
    object Empty: CellsState(R.drawable.ic_empty)
}