package com.example.racingfitness.viewModel

import androidx.lifecycle.ViewModel
import com.example.racingfitness.data.AttendanceRepository
import com.google.firebase.auth.FirebaseUser
import java.util.Calendar

class AttendanceViewModel : ViewModel() {
    private val attendanceRepository = AttendanceRepository()

//    suspend fun markAttendance(userEmail: String, menuItemId: FirebaseUser?, selectedDate: Calendar) {
//        attendanceRepository.markAttendance(userEmail, menuItemId, selectedDate)
//    }
}

