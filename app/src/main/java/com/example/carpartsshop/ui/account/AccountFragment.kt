package com.example.carpartsshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carpartsshop.databinding.FragmentAccountBinding
import com.example.carpartsshop.ui.signin.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentAccountBinding is null")

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser
        user?.let {
            val email = it.email
            binding.tvUserEmail.text = email ?: "No Email Available"
        }

        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()

            val intent = Intent(requireContext(), SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }
}