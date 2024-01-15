package com.example.racingfitness.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.example.racingfitness.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialising auth object
        auth = Firebase.auth

        binding.alreadyHaveAccountTextview.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.signupBtn.setOnClickListener {
            var email = binding.emailEdt.text.toString().trim()
            var name = binding.nameEdt.text.toString().trim()
            var password = binding.passwordEdt.text.toString().trim()
            var confirmPass = binding.confirmPass.text.toString().trim()

                    // check pass
        if (email.isBlank() || name.isBlank() || password.isBlank() || confirmPass.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }

        if (password != confirmPass) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return@setOnClickListener
        }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {

        var mEmail = binding.emailEdt.text.toString().trim()
        var mPassword = binding.passwordEdt.text.toString().trim()

        val minPasswordLength = 8
        if (mEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show()
        } else if (mPassword.length < minPasswordLength) {
            Toast.makeText(
                this,
                "Password must be at least $minPasswordLength characters long",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}
