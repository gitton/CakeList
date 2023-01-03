package com.waracle.cakelist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.waracle.cakelist.data.CakeItem
import com.waracle.cakelist.databinding.ActivityMainBinding
import com.waracle.cakelist.repository.ResultsState
import com.waracle.cakelist.ui.CakeListAdapter
import com.waracle.cakelist.ui.CakeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CakeListAdapter.Listener {
    private lateinit var binding: ActivityMainBinding

    private val viewModel  by viewModels<CakeListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val cakeListAdapter = CakeListAdapter(this)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh_menu -> {
                viewModel.getCakes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClickCake(item: CakeItem) {
        displayDescription(item.title,item.desc)
    }

    //Method to show the dialog with description
    private fun displayDescription(title : String,description  :String){
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            }
            builder.setTitle(R.string.cake_details)
            builder.setMessage("$title : $description")
            // Create the AlertDialog
            builder.create()
        }
        alertDialog.show()
    }
}