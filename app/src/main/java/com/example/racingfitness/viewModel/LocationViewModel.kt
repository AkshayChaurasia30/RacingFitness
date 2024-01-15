package com.example.racingfitness.viewModel
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    val selectedDate = MutableLiveData<String>()
    val currentLocation = MutableLiveData<Location>()
}
