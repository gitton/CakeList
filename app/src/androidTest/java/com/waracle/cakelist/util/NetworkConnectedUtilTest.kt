package com.waracle.cakelist.util


import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Test


class NetworkConnectedUtilTest{

     @Test
     fun when_networkConnected_return_true(){
         val networkConnectedUtil = NetworkConnectedUtil(InstrumentationRegistry.getInstrumentation().getContext())
         assertTrue(networkConnectedUtil.isOnline())
     }

 }