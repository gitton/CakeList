package com.waracle.cakelist.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.waracle.cakelist.MainDispatcherRule
import com.waracle.cakelist.data.CakeItem
import com.waracle.cakelist.datasource.RemoteDataSource
import com.waracle.cakelist.util.NetworkConnectedUtil
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CakeListRepositoryTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cakeListRepository : CakeListRepository

    private val remoteDataSource : RemoteDataSource = mock()
    private val networkConnectedUtil : NetworkConnectedUtil = mock()

    @Test
    fun when_getCakesInvoked_RequestCakesListFromDataSource() = runBlockingTest{
        val mockList = mock<List<CakeItem>>()
        whenever(remoteDataSource.getCakeList()).thenReturn(mockList)
        whenever(networkConnectedUtil.isOnline()).thenReturn(true)
        cakeListRepository = CakeListRepository(remoteDataSource,networkConnectedUtil)
        cakeListRepository.getCakes().first()
        verify(remoteDataSource, times(1)).getCakeList()
    }

    @Test
    fun when_getCakeListSuccess_EmitResultStateSuccess() = runBlockingTest{
        val mockList = mock<List<CakeItem>>()
        val successResponse = ResultsState.Success(mockList)
        whenever(remoteDataSource.getCakeList()).thenReturn(mockList)
        whenever(networkConnectedUtil.isOnline()).thenReturn(true)
        cakeListRepository = CakeListRepository(remoteDataSource,networkConnectedUtil)
        val resultState = cakeListRepository.getCakes().first()
        assertEquals(resultState,successResponse)
    }

    @Test
    fun when_getCakeListNoNetworkError_EmitResultStateNoNetworkError() = runBlockingTest{
        val networkErrorResponse = ResultsState.NoNetworkError
        whenever(networkConnectedUtil.isOnline()).thenReturn(false)
        cakeListRepository = CakeListRepository(remoteDataSource,networkConnectedUtil)
        val resultState = cakeListRepository.getCakes().first()
        assertEquals(resultState,networkErrorResponse)
    }

    @Test
    fun when_getCakeListNoNetworkError_doNotInvokeGetCakesFromDataSource() = runBlockingTest{
        val mockList = mock<List<CakeItem>>()
        val networkErrorResponse = ResultsState.NoNetworkError
        whenever(remoteDataSource.getCakeList()).thenReturn(mockList)
        whenever(networkConnectedUtil.isOnline()).thenReturn(false)
        cakeListRepository = CakeListRepository(remoteDataSource,networkConnectedUtil)
        val resultState = cakeListRepository.getCakes().first()
        verify(remoteDataSource, times(0)).getCakeList()
    }

    @Test
    fun when_getCakeListNetworkConnected_InvokeGetCakesFromDataSource() = runBlockingTest{
        val mockList = mock<List<CakeItem>>()
        whenever(remoteDataSource.getCakeList()).thenReturn(mockList)
        whenever(networkConnectedUtil.isOnline()).thenReturn(true)
        cakeListRepository = CakeListRepository(remoteDataSource,networkConnectedUtil)
        cakeListRepository.getCakes().first()
        verify(remoteDataSource, times(1)).getCakeList()
    }

    @Test
    fun when_getCakeListThrowError_EmitResultStateUnknownError() = runBlockingTest{
        whenever(remoteDataSource.getCakeList()).thenThrow(RuntimeException())
        whenever(networkConnectedUtil.isOnline()).thenReturn(true)
        cakeListRepository = CakeListRepository(remoteDataSource,networkConnectedUtil)
        val response = cakeListRepository.getCakes().first()
        assertEquals(response,ResultsState.UnKnownError)
    }

}