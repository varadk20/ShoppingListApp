package com.example.shoppinglistapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(newLocation: LocationData) {
        _location.value = newLocation
    }

    fun fetchAddress(latlng: String) {
        val apiKey = BuildConfig.API_KEY
        try {
            viewModelScope.launch {
                val result = RetroFitClient.create().getAddressFromCoordinates(
                    latlng,
                    apiKey
                )
                _address.value = result.results
            }
        } catch (e: Exception) {
            Log.e("fetchAddress", "Error fetching address: ${e.message}")
        }
    }
}
