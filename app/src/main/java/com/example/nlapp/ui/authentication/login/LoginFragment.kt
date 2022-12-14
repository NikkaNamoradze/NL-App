package com.example.nlapp.ui.authentication.login

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.example.nlapp.R
import com.example.nlapp.databinding.FragmentLoginBinding
import com.example.nlapp.ui.base.BaseFragment
import com.example.nlapp.utils.AdminAuth
import com.example.nlapp.utils.Validator.isEmailEmpty
import com.example.nlapp.utils.Validator.isEmailValid
import com.example.nlapp.utils.Validator.isPasswordEmpty
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {


    override fun start() {

        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCryptoFragment())
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCryptoFragment())
        }

        listeners()
    }


    private fun listeners() {
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.forgetPassText.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRecoveryFragment())
        }

        binding.loginBtn.setOnClickListener {

            if (binding.emailEditText.text.toString() == AdminAuth.ADMIN_EMAIL && binding.passwordEditText.text.toString() == AdminAuth.ADMIN_PASSWORD) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAdminFragment())
            } else {
                login()
            }

        }
        binding.privacyText.setOnClickListener {
            redirect()
        }
    }


    private fun login() {
        binding.apply {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isEmailEmpty(email) || !isEmailValid(email)) {
                emailInputLayout.error = getString(R.string.enter_your_email)
            } else {
                emailInputLayout.error = null
            }
            if (isPasswordEmpty(password)) {
                passwordInputLayout.error = getString(R.string.enter_correct_password)
            } else {
                passwordInputLayout.error = null

            }
            if (!isEmailEmpty(email) && !isPasswordEmpty(password)) {
                authentication(email, password)

            }
        }

    }


    private fun authentication(email: String, password: String) {
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    navigate()
                } else {
                    binding.emailInputLayout.error = getString(R.string.not_match)
                }
            }
    }

    private fun navigate() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCryptoFragment())
    }

    private fun redirect() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url)))
        startActivity(intent)
    }

}