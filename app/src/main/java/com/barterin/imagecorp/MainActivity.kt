package com.barterin.imagecorp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.theartofdev.edmodo.cropper.CropImage


class MainActivity : AppCompatActivity() {

    private val cropActivityResultContracts = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16, 9)
                .getIntent(this@MainActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }

    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnChooseImage = findViewById<Button>(R.id.btnChooseImage)
        val ivCroppedImage = findViewById<ImageView>(R.id.ivCroppedImage)
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContracts) {
            it?.let { uri ->
                ivCroppedImage.setImageURI(uri)
            }
        }


        btnChooseImage.setOnClickListener {
            cropActivityResultLauncher.launch(null)
        }



    }
}