package com.example.racingfitness.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.racingfitness.R
import com.example.racingfitness.adapter.CarouselAdapter
import com.example.racingfitness.databinding.ActivityStudentProfileBinding
import com.example.racingfitness.model.Profile
import com.example.racingfitness.viewModel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.abs


class StudentProfile : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: CarouselAdapter
    private lateinit var runnable:Runnable
    private var db = Firebase.firestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: ActivityStudentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewpagerItems()
//        setupTransformer()

        binding.viewpagerView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
//                onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed((runnable), 2000)

            }
        })
         runnable =Runnable {
            binding.viewpagerView.currentItem = binding.viewpagerView.currentItem + 1
        }
        // Initialize ViewModel
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        // Observe changes in the profile data
        profileViewModel.profile.observe(this) { profile ->
            updateUI(profile)
        }
        // Set sample profile data
        profileViewModel.setSampleProfile()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,2000)
    }

    private fun viewpagerItems() {
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.sai_one)
        imageList.add(R.drawable.sai_two)
        imageList.add(R.drawable.sai_three)
        imageList.add(R.drawable.sai_four)
        imageList.add(R.drawable.sai_five)
        imageList.add(R.drawable.sai_six)

        adapter = CarouselAdapter(imageList,binding.viewpagerView)
        binding.viewpagerView.adapter = adapter
        binding.viewpagerView.offscreenPageLimit =3
        binding.viewpagerView.clipToPadding = false
        binding.viewpagerView.clipChildren= false
        binding.viewpagerView.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    private fun updateUI(profile: Profile) {
            // Initialize views
            val profileImage = binding.profileImage
            val nameTextView = binding.nameTextView
            val ageTv =  binding.ageTv
        val sportTv = binding.sportTextView
            val achievementsTextView =  binding.achievementsTextView

            // Set data to views
            profileImage.setImageResource(profile.imageResourceId)
            nameTextView.text = profile.name
            ageTv.text= profile.age.toString()
            sportTv.text = profile.sport
            achievementsTextView.text = profile.sport


            // Format achievements as a bulleted list
            val achievementsText = profile.achievements.joinToString(separator = "\n\u2022 ")
            achievementsTextView.text = "\u2022 $achievementsText"
        }
    }
