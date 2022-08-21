package com.example.nlapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nlapp.MainActivity
import com.example.nlapp.R


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_splash, container, false)

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        },3000)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val activity = requireActivity() as? MainActivity
//        activity?.hideNavBar()

    }

}