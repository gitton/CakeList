package com.waracle.cakelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.waracle.cakelist.databinding.ActivityMainBinding
import com.waracle.cakelist.ui.CakeListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var adapter = CakeListAdapter()

        with(binding.rvCakeList){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapter
        }
    }
}