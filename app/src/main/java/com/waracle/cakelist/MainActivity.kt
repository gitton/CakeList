package com.waracle.cakelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.waracle.cakelist.databinding.ActivityMainBinding
import com.waracle.cakelist.repository.ResultsState
import com.waracle.cakelist.ui.CakeListAdapter
import com.waracle.cakelist.ui.CakeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel  by viewModels<CakeListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val cakeListAdapter = CakeListAdapter()

        with(binding.rvCakeList){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cakeListAdapter
        }
        viewModel.viewState.observe(this) { resultState ->
            if(resultState is ResultsState.Success)
            {
                cakeListAdapter.setItems(resultState.list)
            }
        }
    }
}