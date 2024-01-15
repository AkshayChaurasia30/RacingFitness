package com.example.racingfitness.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.racingfitness.R
import com.example.racingfitness.data.AttendanceRepository
import com.example.racingfitness.model.AttendanceRecord
import com.example.racingfitness.model.MenuItemRecord
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.Calendar

// MainViewModel.kt
class MainViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<Calendar>()
    val selectedDate: LiveData<Calendar> get() = _selectedDate
    private val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
    private lateinit var repository:AttendanceRepository
    private val _attendanceMarked = MutableLiveData<Boolean>()
     fun setDataInList(): ArrayList<MenuItemRecord> {

        var items: ArrayList<MenuItemRecord> = ArrayList()
        items.add(MenuItemRecord(R.drawable.available, "Attendance", 0))
        items.add(MenuItemRecord(R.drawable.user, "User Profile", 1))
        items.add(MenuItemRecord(R.drawable.coach, "Faculty", 2))
        items.add(MenuItemRecord(R.drawable.support, "Help", 3))
        items.add(MenuItemRecord(R.drawable.money, "Fee", 4))
        items.add(MenuItemRecord(R.drawable.performance, "Performance", 5))
        items.add(MenuItemRecord(R.drawable.event, "Events", 6))
        items.add(MenuItemRecord(R.drawable.travel, "Travel", 7))
        items.add(MenuItemRecord(R.drawable.achivement, "Achievements", 8))
        return items

    }
}
