package com.example.zippyservicejson.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zippyservicejson.network.ZipcodeApi
import com.example.zippyservicejson.network.ZipcodeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<ZipcodeResponse>()
    val response: LiveData<ZipcodeResponse> = _response

    fun getZipInfo(zip: String) {

        viewModelScope.launch {
            try {
                delay(2000)     // artificial delay... notice the UI is still responsive...
                _response.value = ZipcodeApi.retrofitService.getProperties(zip)
            } catch (ex: Exception) {
                print("************************************************")
                ex.printStackTrace()
                print("************************************************")
            }
            showData()
        }
    }

    /*
     showData() - just a convenience method for the logger
     */
    fun showData() {
        Log.i("Frank", "*****RESPONSE")
        Log.i("Frank", "  zipcode: [ ${_response.getValue()?.zipcode} ]")
        Log.i("Frank", "  country: [ ${_response.getValue()?.country} ]")
        Log.i("Frank", "    state: [ ${_response.getValue()?.places?.get(0)?.state} ]")
        Log.i("Frank", "     city: [ ${_response.getValue()?.places?.get(0)?.place_name} ]")
        Log.i("Frank", " latitude: [ ${_response.getValue()?.places?.get(0)?.latitude} ]")
        Log.i("Frank", "longitude: [ ${_response.getValue()?.places?.get(0)?.longitude} ]")

    }
}