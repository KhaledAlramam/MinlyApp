package com.sedra.minlyapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.sedra.minlyapp.R
import com.sedra.minlyapp.databinding.ActivityMainBinding
import com.sedra.minlyapp.utils.Status.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel>()
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadImages()
    }

    private fun loadImages() {
        viewModel.getImageList().observe(this){
            it?.let { resource ->
                when(resource.status){
                    SUCCESS -> {
                        displayImagesOnRv()
                    }
                    ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        Toast.makeText(this, getString(R.string.please_wait), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun displayImagesOnRv() {

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}