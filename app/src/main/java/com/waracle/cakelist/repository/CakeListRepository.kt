package com.waracle.cakelist.repository

import com.waracle.cakelist.datasource.RemoteDataSource
import com.waracle.cakelist.util.NetworkConnectedUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CakeListRepository @Inject constructor(private val remoteDataSource : RemoteDataSource, private val networkConnected : NetworkConnectedUtil) {
   private val TAG = CakeListRepository::class.java.simpleName

    suspend fun getCakes() : Flow<ResultsState> {
        return flow {
            if(networkConnected.isOnline()) {

                emit(ResultsState.Success(remoteDataSource.getCakeList().distinct().sortedBy { it.title }))
            }else{
                emit(ResultsState.NoNetworkError)
            }
        }.catch { emit(ResultsState.UnKnownError) }
    }

}
