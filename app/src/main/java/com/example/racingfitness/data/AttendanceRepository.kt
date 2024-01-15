package com.example.racingfitness.data

import android.icu.text.SimpleDateFormat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Locale

class AttendanceRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun markAttendance(userEmail: String, menuItemId: Int, selectedDate: Calendar) {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(selectedDate.time)

        val userAttendanceDoc = firestore.collection("attendance").document(userEmail)

        // Use a single transaction to ensure atomicity
        firestore.runTransaction { transaction ->
            val userDocSnapshot = transaction.get(userAttendanceDoc)

            if (userDocSnapshot.exists()) {
                // User document exists, update attendance for the specific date
                transaction.set(
                    userAttendanceDoc.collection("dates").document(formattedDate),
                    mapOf("menuItemId" to menuItemId)
                )
            } else {
                // User document does not exist, create a new document and add attendance
                val userData = hashMapOf(
                    "name" to userEmail,
                    "email" to userEmail
                )

                transaction.set(userAttendanceDoc, userData)
                transaction.set(
                    userAttendanceDoc.collection("dates").document(formattedDate),
                    mapOf("menuItemId" to menuItemId)
                )
            }
        }
    }
}
