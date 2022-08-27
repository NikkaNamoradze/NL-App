package com.example.nlapp.ui.cryptoitem

import android.annotation.SuppressLint
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.nlapp.MainActivity
import com.example.nlapp.databinding.CryptoItemFragmentBinding
import com.example.nlapp.extensions.setImage
import com.example.nlapp.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CryptoItemFragment :
    BaseFragment<CryptoItemFragmentBinding>(CryptoItemFragmentBinding::inflate) {

    private val args: CryptoItemFragmentArgs by navArgs()

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideNavBar()
        setCryptoItemData()
        listeners()
        addToFavorites()
    }

    private fun listeners() {
        binding.vBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        binding.btnAddToFavorite.setOnClickListener {
            addToFavorites()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCryptoItemData() {
        binding.apply {
            ivCryptoImage.setImage(args.cryptoItem.image)
            tvCryptoName.text = args.cryptoItem.name
            tvCryptoPrice.text =
                "${tvCryptoPrice.text}" + args.cryptoItem.currentPrice.toString() + "$"
            tvCryptoHigh.text = "${tvCryptoHigh.text}" + args.cryptoItem.high24h.toString() + "$"
            tvCryptoLow.text = "${tvCryptoLow.text}" + args.cryptoItem.low24h.toString() + "$"
            tvCryptoLastUpdate.text =
                "${tvCryptoLastUpdate.text}" + args.cryptoItem.lastUpdated.substring(0, 10)
            tvCryptoRank.text = "${tvCryptoRank.text}" + args.cryptoItem.marketCapRank.toString()
            tvCryptoPriceChange.text =
                "${tvCryptoPriceChange.text}" + args.cryptoItem.priceChange24h.toString()
                    .substring(0, 7) + "$"
        }
    }

    private fun addToFavorites() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseDatabase.getInstance().getReference("User")

        db.child(auth.currentUser?.uid!!).child("Favorite").child(args.cryptoItem.name)
            .setValue(args.cryptoItem)
    }
}