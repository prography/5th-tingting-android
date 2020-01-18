package com.example.tintint_jw.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_profile_detail.*
import kotlinx.android.synthetic.main.dialog_view.view.*

class ProfileDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)

        Glide.with(newteamProfileImg).load(R.drawable.haein)
            .apply(RequestOptions.circleCropTransform()).into(newteamProfileImg)

        // 프로필 사진 바꾸기

        changeImg.setOnClickListener(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(READ_EXTERNAL_STORAGE)==
                        PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    pickImageFromGallery()
                }
            }else{
                pickImageFromGallery()
            }
        }


        saveInfo.setOnClickListener(){
            val checkDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_view, null)

            checkDialog.setView(dialogView)

            val check = checkDialog.show()
            val drawable = resources.getDrawable(R.drawable.dialog)

            Glide.with(newteamProfileImg).load(R.drawable.haein)
                .apply(RequestOptions.circleCropTransform()).into(newteamProfileImg)

            dialogView.dialogCancel.setOnClickListener(){
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener(){
                finish()
                Toast.makeText(this, "변경사항이 저장되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener(){
            finish()
        }

        // 로그아웃

        logout.setOnClickListener(){
            val logoutDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_logout,null)

            logoutDialog.setView(dialogView)
            val check = logoutDialog.show()

            dialogView.dialogCancel.setOnClickListener(){
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener(){
                App.prefs.mypassword="-1-1"
                App.prefs.myautoLogin="false"
                finishAffinity()
            }
        }
    }

    private fun pickImageFromGallery() {
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
                    Toast.makeText(this, "권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            Glide.with(newteamProfileImg).load(data?.data)
                .apply(RequestOptions.circleCropTransform()).into(newteamProfileImg)


        }
    }

}