package com.example.racingfitness.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.racingfitness.R
import com.example.racingfitness.databinding.FragmentHelpBinding

// HelpFragment.kt
    class HelpFragment : Fragment() {

    // Doctors' contact numbers
    private val doctor1Contact = "tel:1234567890"
    private val doctor2Contact = "tel:9876543210"

    // UI elements
    private lateinit var binding: FragmentHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(inflater,container,false)
        binding.doctor2TextView.text.toString()
        binding.callDoctorBtn.setOnClickListener {
            view?.let { it1 -> onCallDoctorButtonClick(it1) }
        }
       return binding.root
        // Initialize UI elements

    }


        private fun onCallDoctorButtonClick(view: View) {
            when (view.id) {
                R.id.callDoctorBtn -> {
                    // Call the first doctor (you can customize this logic)
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(doctor1Contact)))
                }
            }
        }
    }
