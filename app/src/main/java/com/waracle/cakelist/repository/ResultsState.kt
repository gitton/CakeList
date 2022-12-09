package com.waracle.cakelist.repository

import com.waracle.cakelist.data.CakeItem

sealed class ResultsState{
    data class Success(val list : List<CakeItem>)  : ResultsState()
    object NoNetworkError : ResultsState()
    object UnKnownError : ResultsState()

}