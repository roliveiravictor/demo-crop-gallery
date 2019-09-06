package com.stonetree.imagebucket.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stonetree.imagebucket.accessField
import com.stonetree.imagebucket.main.resources.repository.MainRepositoryImpl
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.any
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private val vm: MainViewModel = MainViewModel(MainRepositoryImpl())

    @Test
    fun test_viewModelRepository_shouldReturnNotEmpty() {
        val repository = vm
            .accessField("repository")
                as MainRepositoryImpl

        assertNotNull(repository)
    }

    @Test
    fun test_viewModelImages_shouldReturnMutableLiveData() {
        assertThat(vm.images, `is`(any(LiveData::class.java)))
    }
}