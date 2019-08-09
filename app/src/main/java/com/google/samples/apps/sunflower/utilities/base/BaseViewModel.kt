package com.google.samples.apps.sunflower.utilities.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.samples.apps.sunflower.utilities.helper.Event

open class BaseViewModel : ViewModel() {

    val isRequesting = MutableLiveData<Event<Boolean>>()
    val showMessage = MutableLiveData<Event<String>>()
    val showMessageRes = MutableLiveData<Event<Int>>()
}