package com.example.racingfitness.model

import java.util.Calendar

data class AttendanceRecord(
    val id: Int,
    val name: String,
    val date: Calendar,
    val status: String
)

