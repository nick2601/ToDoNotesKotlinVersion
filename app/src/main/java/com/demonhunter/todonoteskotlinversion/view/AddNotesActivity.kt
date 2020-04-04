package com.demonhunter.todonoteskotlinversion.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.demonhunter.todonoteskotlinversion.BuildConfig
import com.demonhunter.todonoteskotlinversion.R
import com.demonhunter.todonoteskotlinversion.utils.AppConstant
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class AddNotesActivity : AppCompatActivity() {
    val TAG: String = "AddNotesActivity"
    private val MY_PERMISSION_CODE = 124
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var btnSubmit: Button
    lateinit var imageViewNotes: ImageView
    val REQUEST_CODE_CAMERA = 1
    val REQUEST_CODE_GALLERY = 2
    var picturePath = ""
    lateinit var imageLocation: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindViews()
        clickListener()
    }

    private fun clickListener() {  // in this we are passing the data from addNotesActivity to myNotesActivity
        btnSubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra(AppConstant.TITLE, editTextTitle.text.toString())
                intent.putExtra(AppConstant.DESCRIPTION, editTextDescription.text.toString())
                intent.putExtra(AppConstant.IMAGE_PATH, picturePath)
                setResult(Activity.RESULT_OK, intent)
                finish() // kills the given activity and stops the flow
            }

        })
        imageViewNotes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (checkAndRequestPermissions()) { //checking for the permission and if it is not there request for the same & if it there just open the dialog
                    setupDialog()

                }
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
    }


    private fun setupDialog() {
        val view =
            LayoutInflater.from(this@AddNotesActivity).inflate(R.layout.dialog_selector, null)
        val textViewCamera = view.findViewById<TextView>(R.id.textViewCamera)
        val textViewGallery = view.findViewById<TextView>(R.id.textViewGallery)
        val dialog = android.app.AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()
        textViewCamera.setOnClickListener(object :
            View.OnClickListener { //here we will capture the image from camera and load in the imageView
            override fun onClick(v: View?) {
                val takePictureIntent =
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)//intent is created to capture a img through camera
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    // Create the File where the photo should go
                    var photoFile: File? = null
                    try {
                        photoFile =
                            createImageFile() //here a create a photo file and assign it to create image file
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                        // Error occurred while creating the File
                    }

                    if (photoFile != null) {  //in this we are getting path for the image from local storage uri
                        val photoURI = FileProvider.getUriForFile(
                            this@AddNotesActivity,
                            BuildConfig.APPLICATION_ID + ".provider",
                            photoFile
                        )
                        picturePath = photoFile.absolutePath
                        Log.d(TAG, picturePath)
                        imageLocation = photoFile
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(
                            takePictureIntent,
                            REQUEST_CODE_CAMERA
                        ) // a result is expected after clicking the image
                    }
                }
                dialog.hide()
            }
        })
        textViewGallery.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
                dialog.hide()
            }
        })
        dialog.show()
    }

    private fun bindViews() {
        editTextTitle = findViewById(R.id.et_title)
        imageViewNotes = findViewById(R.id.imageViewNotes)
        editTextDescription = findViewById(R.id.et_description)
        btnSubmit = findViewById(R.id.btn_submit)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            REQUEST_CODE_CAMERA -> Glide.with(this).load(imageLocation.absoluteFile)
                .into(imageViewNotes)
            REQUEST_CODE_GALLERY -> {  // we have taken image from gallery and assigned it to a picture path
                val selectedImage = data?.data
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val c = contentResolver.query(selectedImage!!, filePath, null, null, null)
                //cursors are use to do some query on a global level in an android app
                c?.moveToFirst()                                                //we move to first row and then first column and then we got column index
                val columnIndex = c!!.getColumnIndex(filePath[0])
                picturePath = c.getString(columnIndex)
                c.close()
                Glide.with(this).load(picturePath).into(imageViewNotes)
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file  //timestamp=2020-02-25-08-35-58
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mFileName = "JPEG_" + timeStamp + "_"
        val storageDir =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)//in the Pictures directory we are registering the file we are creating here
        return File.createTempFile(mFileName, ".jpg", storageDir)
    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissionCAMERA =
            ContextCompat.checkSelfPermission( // checking the permission for storage and camera //ContextCompat is used to access some of the resources of android
                this,
                Manifest.permission.CAMERA
            )
        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val listPermissionsNeeded = ArrayList<String>()
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray<String>(), MY_PERMISSION_CODE
            )
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted Successfully. Write working code here.
                    setupDialog()
                }
            }
        }
    }
}

