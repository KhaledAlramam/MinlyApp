package com.sedra.minlyapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sedra.minlyapp.R
import com.sedra.minlyapp.data.model.GetImageResponse
import com.sedra.minlyapp.databinding.ActivityMainBinding
import com.sedra.minlyapp.utils.Status.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel>()
    var binding: ActivityMainBinding? = null
    var imageAdapter: ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        imageAdapter = ImageAdapter()
        binding?.apply {
            recyclerView.layoutManager =
                GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = imageAdapter
            addPhotoBtn.setOnClickListener {
                pickImage()

            }
        }

        loadImages()
    }

    private fun loadImages() {
        viewModel.getImageList().observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        displayImagesOnRv(resource.data)
                    }
                    ERROR -> {
                        Log.e("TAG", "loadImages: " + resource.message)
                    }
                    LOADING -> {
                        Toast.makeText(this, getString(R.string.please_wait), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun displayImagesOnRv(data: GetImageResponse?) {
        data?.let { response ->
            if (response.data.isNotEmpty()) {
                binding?.apply {
                    recyclerView.isVisible = true
                    noDataPlaceHolder.isVisible = false
                }
                imageAdapter?.submitList(data.data)
            } else {
                binding?.apply {
                    recyclerView.isVisible = false
                    noDataPlaceHolder.isVisible = true
                }
            }
        }

    }


    private fun pickImage() {

        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            ).start()
    }

    private fun uploadImage(data: Intent) {
        viewModel.uploadImage(
            ImagePicker.getFilePath(data) ?: ""
        ).observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        resource.data?.let { response ->
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                            loadImages()
                        }
                    }
                    ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    }
                    LOADING -> {
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            uploadImage(data!!)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        binding = null
        imageAdapter = null
        super.onDestroy()
    }
}