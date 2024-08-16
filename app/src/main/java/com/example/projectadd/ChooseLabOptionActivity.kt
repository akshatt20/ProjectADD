package com.example.projectadd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectadd.databinding.ActivityChooseLabOptionBinding

class ChooseLabOptionActivity : AppCompatActivity() {
    private val binding :ActivityChooseLabOptionBinding by lazy{
        ActivityChooseLabOptionBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ivUploadDocument.setOnClickListener{
           val intent= Intent(this,LabReportsActivity::class.java)
            startActivity(intent)
        }
    }
}