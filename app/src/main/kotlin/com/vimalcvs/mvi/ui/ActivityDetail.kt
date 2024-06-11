package com.vimalcvs.mvi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vimalcvs.mvi.databinding.ActivityDetailBinding
import com.vimalcvs.mvi.model.MainViewModel

class ActivityDetail : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val postTitle = intent.getStringExtra("EXTRA_POST_TITLE")
        val postBody = intent.getStringExtra("EXTRA_POST_BODY")

        binding.titleTextView.text = postTitle
        binding.bodyTextView.text = postBody
    }
}
