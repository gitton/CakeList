package com.waracle.cakelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
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
        val fadeInAnimation = getAnimation()

        with(binding.rvCakeList){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = cakeListAdapter
            animation = fadeInAnimation
        }

        //observe live data
        viewModel.viewState.observe(this) { resultState ->
            setProgressBar(View.GONE)
            when (resultState)
            {
                is ResultsState.Success -> {
                    binding.rvCakeList.visibility = View.VISIBLE
                    binding.layoutError.root.visibility = View.GONE
                    cakeListAdapter.setItems(resultState.list)
                }
                ResultsState.NoNetworkError -> {
                    showErrorMessage(getString(R.string.no_internet_connection))
                }
                ResultsState.UnKnownError -> {
                    showErrorMessage(getString(R.string.looks_like_there_was_problem_loading_cake_list))
                }
            }
        }
    }

    //set Progress Bar Visiblity
    private fun setProgressBar(visibility: Int){
        binding.progressBar.visibility = visibility
    }

    //Return fade In Animation
    private fun getAnimation() : Animation{
        val fadeInAnimation = AlphaAnimation(0.0f, 1.0f)
        fadeInAnimation.duration = 500
        return fadeInAnimation
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    //Display error message and option to retry cake list
    private fun showErrorMessage(message : String){
        binding.rvCakeList.visibility = View.GONE
        binding.layoutError.root.visibility = View.VISIBLE
        binding.layoutError.tvErrorMessage.text = message
        binding.layoutError.btnRetry.setOnClickListener() {
            retryCakeList()
        }
    }

    //Retry cake List items
    private fun retryCakeList(){
        setProgressBar(View.VISIBLE)
        viewModel.getCakes()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh_menu -> {
                retryCakeList()
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
                setPositiveButton(R.string.ok){ dialog, id ->
                    }
            }
            builder.setTitle(R.string.cake_details)
            builder.setMessage("$title : $description")
            // Create the AlertDialog
            builder.create()
        }
        alertDialog.show()
    }
}