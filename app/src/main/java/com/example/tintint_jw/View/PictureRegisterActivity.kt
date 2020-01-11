package com.example.tintint_jw.View

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tintint_jw.Model.IdCallBack
import com.example.tintint_jw.Model.ModelSignUp
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_picture_register.*
import java.io.File
import java.net.URL

class PictureRegisterActivity : AppCompatActivity() {
    var model :ModelSignUp = ModelSignUp(this)
    var checkimge = false
    lateinit var file:File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_register)

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
            Log.d("imgPick",imgPick.toString())

            if(!checkimge){
                Toast.makeText(this,"반드시 1장 이상의 사진을 등록해 주세요",Toast.LENGTH_LONG).show()
            }else{
                model.signUP(App.prefs.mylocal_id.toString(),App.prefs.mypassword.toString()
                    ,App.prefs.mygender.toString(),App.prefs.myname.toString(),App.prefs.mybirth.toString()
                    ,App.prefs.mythumnail.toString(),App.prefs.myauthenticated_address.toString(),App.prefs.myheight.toString(),
                    applicationContext)


            }
        }

        back.setOnClickListener(){
            finish()
        }

    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.EXTRA_ALLOW_MULTIPLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_PICK_CODE)

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
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imgPick.visibility = View.INVISIBLE

            Glide.with(setImageView).load(data?.data)
                .apply(RequestOptions.circleCropTransform()).into(setImageView)

            file = File(data.toString())
         //   App.prefs.mythumnail= file.name
            checkimge=true
        }
    }

    }

