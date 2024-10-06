package com.example.carpartsshop.ui.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.carpartsshop.databinding.ActivitySignInBinding
import com.example.carpartsshop.ui.MainActivity
import com.example.carpartsshop.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private var _binding: ActivitySignInBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivitySignInBinding is null")

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        _binding = ActivitySignInBinding.inflate(layoutInflater)

        firebaseAuth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}