package com.example.racingfitness.data

import com.example.racingfitness.model.AttendanceRecord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseManager {
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Get the current authenticated user
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Sign in anonymously (or implement other sign-in methods as needed)
    fun signInAnonymously(callback: (FirebaseUser?) -> Unit) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(auth.currentUser)
            } else {
                callback(null)
            }
        }
    }
    // Write attendance record to Firebase
    fun writeAttendanceRecord(record: AttendanceRecord) {
        val attendanceRef = database.reference.child("attendance_records")
            .child(record.id.toString())
            .child(record.date.toString())
            .push()

        attendanceRef.setValue(record)
    }
    fun readAttendanceRecords(
        eventId: String,
        date: String,
        callback: (List<AttendanceRecord>) -> Unit
    ) {
        val attendanceRef =
            database.reference.child("attendance_records").child(eventId).child(date)

        attendanceRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val records = mutableListOf<AttendanceRecord>()
                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(AttendanceRecord::class.java)
                    record?.let { records.add(it) }
                }
                callback(records)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                callback(emptyList())
            }
        })
    }

    fun initialize() {
    }
}
