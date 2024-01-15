package com.example.racingfitness.model

import android.media.Image

data class Profile(
    val name: String,
    val age: Int,
    val sport: String,
    val achievements: List<String>,
    val imageResourceId: Int // Resource ID for the athlete's image
)

