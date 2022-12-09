package com.waracle.cakelist.ui

import androidx.lifecycle.*
import com.waracle.cakelist.di.EspressoIdllingResource
import com.waracle.cakelist.repository.CakeListRepository
import com.waracle.cakelist.repository.ResultsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakeListViewModel @Inject constructor(private val cakeListRepository: CakeListRepository) : ViewModel() {

    private var _viewState = MutableLiveData<ResultsState>()
    val viewState : LiveData<ResultsState> = _viewState

    init {
        getCakes()
    }

    fun getCakes(){
        viewModelScope.launch {
            EspressoIdllingResource.increment()
            cakeListRepository.getCakes().collect() {
                _viewState.value = it
                EspressoIdllingResource.decrement()
            }
        }
    }

}

