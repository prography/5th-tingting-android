package com.tingting.ver01.View

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.tingting.ver01.Model.CodeCallBack
import com.tingting.ver01.Model.ModelSignUp
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_picture_register.*
import java.io.File


class PictureRegisterActivity : AppCompatActivity() {

    var model :ModelSignUp = ModelSignUp(this)
    var checkimge = false
    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_register)

      //  changeButton()

        back.setOnClickListener {
            finish()
        }

        //BUTTON CLICK
        imgPick.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }


        //여기 모델
        next.setOnClickListener(){

            if(!checkimge){
                Toast.makeText(this,"반드시 한 장 이상의 사진을 등록해 주세요",Toast.LENGTH_LONG).show()
            }else{
                model.uploadThumbnail(uri, object : CodeCallBack {
                    override fun onSuccess(code:String, value: String) {

                    }
                })


            }
        }

        back.setOnClickListener(){
            finish()
        }

    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);

    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                var cropImage = CropImage.getActivityResult(data);

                if (resultCode == Activity.RESULT_OK) {
                    imgPick.visibility = View.INVISIBLE
                    checkimge = true
                    uri = cropImage.uri

                    Glide.with(setImageView).load(cropImage.uri)
                        .apply(RequestOptions.circleCropTransform()).into(setImageView)

                }


            }
        } catch (e: Exception) {
            e.printStackTrace()


        }
    }
//    fun changeButton(){
//        next.isEnabled = checkimge
//    }
}

