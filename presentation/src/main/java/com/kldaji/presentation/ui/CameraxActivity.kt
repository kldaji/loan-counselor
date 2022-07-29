package com.kldaji.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kldaji.presentation.databinding.ActivityCameraxBinding

class CameraxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraxBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
