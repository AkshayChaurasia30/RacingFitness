package com.example.racingfitness.viewModel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.racingfitness.R
import com.example.racingfitness.model.Profile

class ProfileViewModel:ViewModel() {
    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile

    fun setSampleProfile() {
        // Replace this with your actual data retrieval logic
        _profile.value = Profile(
            name = "Sai Kaware",
            age = 16,
            sport = "Achievement",
            achievements = listOf("Gold Medal in Mountain climbing","Gold Medal - 100m Sprint","Gold Medal 3k"),
            imageResourceId = R.drawable.sai_profile_img
        )
    }
}
