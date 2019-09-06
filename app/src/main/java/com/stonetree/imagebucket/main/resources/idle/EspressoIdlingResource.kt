package com.stonetree.imagebucket.main.resources.idle

import androidx.test.espresso.IdlingResource

object EspressoIdlingResource {

    private const val resource = "GLOBAL"

    private val countingIdlingResource = MainIdlingResource(resource)

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.decrement()

    fun getIdlingResource(): IdlingResource = countingIdlingResource


}