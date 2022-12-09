package com.waracle.cakelist.di

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdllingResource {

    val countingIdlingResource = CountingIdlingResource("Global")

    fun increment(){
        countingIdlingResource.increment()
    }

    fun decrement(){
        countingIdlingResource.decrement()
    }
}