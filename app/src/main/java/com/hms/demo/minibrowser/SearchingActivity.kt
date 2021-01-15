package com.hms.demo.minibrowser

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hms.demo.minibrowser.databinding.ActivitySearchBinding
import com.hms.demo.minibrowser.databinding.SearchBinding

class SearchingActivity : AppCompatActivity() {
    lateinit var binding:ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}