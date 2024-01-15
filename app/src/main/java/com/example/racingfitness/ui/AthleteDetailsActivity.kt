package com.example.racingfitness.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.racingfitness.databinding.ActivityAthleteDetailsBinding
import com.example.racingfitness.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AthleteDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAthleteDetailsBinding
    private lateinit var databaseReference: DatabaseReference
    private var db = Firebase.firestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAthleteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // view Model Initialization
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.saveButton.setOnClickListener {
            saveAthleteDetails()
        }
    }
//
    private fun saveAthleteDetails() {
        val firstname = binding.firstNameEdt.text.toString().trim()
        val lastname = binding.lastNameEdt.text.toString().trim()
        val dob = binding.dobEdt.text.toString()
        val address = binding.addressedt.text.toString().trim()
        val event = binding.event.text.toString().trim()

        if (firstname.isEmpty() || lastname.isEmpty() || dob.isEmpty() || address.isEmpty() || event.isEmpty()) {
            Toast.makeText(this, "Please Enter the required field details", Toast.LENGTH_SHORT)
                .show()
        } else {
            var userMap = hashMapOf(
                "firstname" to binding.firstNameEdt.text.toString(),
                "lastname" to binding.lastNameEdt.text.toString(),
                "dob" to binding.dobEdt.text.toString(),
                "address" to binding.addressedt.text.toString(),
                "event" to binding.event.text.toString()
            )

           val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
                db.collection("user").document(userEmail).set(userMap).addOnSuccessListener {
                    Toast.makeText(this,"Successfully added Your data",Toast.LENGTH_SHORT).show()
                    binding.firstNameEdt.text.clear()
                    binding.lastNameEdt.text.clear()
                    binding.dobEdt.text.clear()
                    binding.addressedt.text.clear()
                    binding.event.text.clear()
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                    .addOnFailureListener {
                        Toast.makeText(this,"There is some problem in adding Your data",Toast.LENGTH_SHORT).show()
                    }
            }
            }
        }