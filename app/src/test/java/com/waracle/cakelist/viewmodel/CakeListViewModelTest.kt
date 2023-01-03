package com.waracle.cakelist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.waracle.cakelist.MainDispatcherRule
import com.waracle.cakelist.data.CakeItem
import com.waracle.cakelist.repository.CakeListRepository
import com.waracle.cakelist.repository.ResultsState
import com.waracle.cakelist.ui.CakeListViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CakeListViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

     private val cakeListRepository : CakeListRepository = mock()
     private lateinit var viewModel : CakeListViewModel


    @Test
     fun invoke_getCake_fromCakeListRepository() = runBlockingTest {
        val response = ResultsState.Success(mock<List<CakeItem>>())
        whenever(cakeListRepository.getCakes()).thenReturn(flow{
            emit(response)
        })
        viewModel = CakeListViewModel(cakeListRepository)
        verify(cakeListRepository, times(1)).getCakes()
     }

    @Test
    fun when_cakeListSuccess_expect_liveDataCaptureListOfCakes()= runBlockingTest {
        val response = ResultsState.Success(mock<List<CakeItem>>())
        viewModel = CakeListViewModel(cakeListRepository)
        whenever(cakeListRepository.getCakes()).thenReturn(flow{
            emit(response)
        })
        viewModel.getCakes()
        assertEquals(response,viewModel.viewState.value)
    }

    @Test
    fun when_cakeListNetworkError_expect_liveDataCaptureNoNetworkError()= runBlockingTest {
        val response = ResultsState.NoNetworkError
        viewModel = CakeListViewModel(cakeListRepository)
        whenever(cakeListRepository.getCakes()).thenReturn(flow{
            emit(response)
        })
        viewModel.getCakes()
        assertEquals(response,viewModel.viewState.value)
    }



}