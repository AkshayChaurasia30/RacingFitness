package com.example.racingfitness.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.racingfitness.R
import com.example.racingfitness.databinding.FragmentFeeBinding
class FeeFragment : Fragment() {
    private var isImageSelected: Boolean = false
    private lateinit var binding: FragmentFeeBinding
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitFeeButton.visibility = View.GONE

        // Set up a click listener for the ImageView
        binding.submitFeeImg.setOnClickListener {
            // Open the image picker
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Set up a click listener for the submit fee button
        binding.submitFeeButton.setOnClickListener {
            if (isImageSelected) {

                isImageSelected = false
                selectedImageUri = null
                binding.submitFeeImg.setImageResource(R.drawable.payment_receipt) // Clear the image
                binding.submitFeeButton.visibility = View.GONE
                // Display a Toast indicating fee successfully paid
              Toast.makeText(requireContext(),"You have Submitted the Fee",Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Handle the selected image
            val selectedImageUri = data.data
            binding.submitFeeImg.setImageURI(selectedImageUri)
            isImageSelected = true

            // Make the submit button visible
            binding.submitFeeButton.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
