package com.example.racingfitness.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.racingfitness.R
import com.example.racingfitness.adapter.MenuItemAdapter
import com.example.racingfitness.data.FirebaseManager
import com.example.racingfitness.databinding.ActivityMainBinding
import com.example.racingfitness.fragments.FeeFragment
import com.example.racingfitness.fragments.HelpFragment
import com.example.racingfitness.fragments.PerformanceFragment
import com.example.racingfitness.model.MenuItemRecord
import com.example.racingfitness.viewModel.AttendanceViewModel
import com.example.racingfitness.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), MenuItemAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var currentUser: FirebaseUser? = null
    private var database = FirebaseDatabase.getInstance()
    private var auth = FirebaseAuth.getInstance()
    private lateinit var arrayList: ArrayList<MenuItemRecord>
    private var userId = auth.currentUser
    private var attendanceRef = FirebaseDatabase.getInstance().reference
    private lateinit var adapter: MenuItemAdapter
    val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
    private lateinit var attendanceViewModel: AttendanceViewModel
    private val fragmentManager = supportFragmentManager
    private val fragmentTransaction = fragmentManager.beginTransaction()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // Initialize Firebase
        var auth = FirebaseAuth.getInstance()
        var userId = auth.currentUser

        FirebaseManager.initialize()
        // Check if the user is already authenticated
        currentUser = FirebaseManager.getCurrentUser()

        if (currentUser == null) {
            // If not authenticated, sign in anonymously
            FirebaseManager.signInAnonymously { user ->
                currentUser = user
            }
            database = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()
        }
        arrayList = setDataInList()
        adapter = MenuItemAdapter(arrayList, this)
        binding.menuItemRecyclerview.layoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        binding.menuItemRecyclerview.setHasFixedSize(true)
        binding.menuItemRecyclerview.adapter = adapter

        adapter!!.notifyDataSetChanged()


        //attendance viewModel..
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]
        // Observe the markedAttendance LiveData to update UI when attendance is marked

    }

    private fun setDataInList(): ArrayList<MenuItemRecord> {

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

    private fun fetchAndDisplayAttendanceStatus() {
        val attendanceCollection = FirebaseFirestore.getInstance().collection("attendance")
        val userAttendanceDoc = attendanceCollection.document(userEmail)

        // Fetch attendance data for the current user
        userAttendanceDoc.collection("dates").get().addOnSuccessListener { result ->
            for (document in result) {
                val dateStr = document.id
                val menuItemId = document.getLong("menuItemId")

                // Display or process the attendance data as needed
                try {
                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr)
                    val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                    val status = "Attendance marked for $formattedDate with MenuItemId: $menuItemId"
                    Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
                } catch (e: ParseException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to parse date", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to fetch attendance data", Toast.LENGTH_SHORT).show()
        }
    }

        // Date Picker function...
        private fun showDatePicker(record: MenuItemRecord) {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    //calling mark attendance fun on date-picker
//                    markAttendance(record.menuItemId, selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            // Set the maximum date to the current date
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis
            // Set the maximum date to the current date
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        private fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(Date())
        }

        override fun onItemClick(record: MenuItemRecord) {
            when (record.menuItemId) {
                0 -> showDatePicker(record)
                1 -> startActivity(Intent(this, StudentProfile::class.java))
                2 ->  {
                    replaceFragment(PerformanceFragment())
                }
                3 ->  {
                    replaceFragment(HelpFragment())
                }
                4 -> {
                    replaceFragment(FeeFragment())
                }
                5 ->  {
                    replaceFragment(HelpFragment())
                }
                6 -> {
                    replaceFragment(HelpFragment())
                }
                7 ->  {
                    replaceFragment(HelpFragment())
                }
                8 ->  {
                    replaceFragment(HelpFragment())
                }

            }
            fragmentTransaction.commitNow()
//            Toast.makeText(this, record.menuItemId.toString(), Toast.LENGTH_SHORT).show()
        }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)  // Add to back stack if you want to navigate back
        fragmentTransaction.commitAllowingStateLoss()
    }
}


