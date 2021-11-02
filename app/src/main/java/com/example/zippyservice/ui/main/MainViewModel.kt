package com.example.zippyservice.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zippyservice.network.ZipcodeApi
import com.example.zippyservice.network.ZipcodeResponse
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<ZipcodeResponse>()
    val response: LiveData<ZipcodeResponse> = _response

    fun getZipInfo(zip: String) {

        viewModelScope.launch {
            _response.value = ZipcodeApi.retrofitService.getProperties(zip)
            showData()
        }
    }

    fun showData() {
        Log.i("Frank", "*****RESPONSE")
        Log.i("Frank", "zipcode: [ ${_response.getValue()?.zipcode} ]")
        Log.i("Frank", "country: [ ${_response.getValue()?.country} ]")
        Log.i("Frank", "  state: [ ${_response.getValue()?.places?.get(0)?.state} ]")
        Log.i("Frank", "   city: [ ${_response.getValue()?.places?.get(0)?.place_name} ]")

    }
}