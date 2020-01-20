package com.example.tintint_jw.View

import GetProfileResponse
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tintint_jw.Model.ModelSignUp
import com.example.tintint_jw.Model.ProfileCallBack
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_profile_detail.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileDetailActivity : AppCompatActivity() {

    var model : ModelSignUp = ModelSignUp(this)
    var p = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)


        //init screen data
        model.getProfile(App.prefs.myToken.toString(), object : ProfileCallBack{

            override fun onSuccess2(
                name: String,
                birth: String,
                height: String,
                thumnail: String,
                gender: String,
                school : String,
                myTeamData: List<GetProfileResponse.Data.MyTeam>
            ) {

                val scope = CoroutineScope(Dispatchers.Main)

                runBlocking {
                    Log.d("DetailprofileTest","프로필 설정")
                    scope.launch {
                        //이름 설정
                        profileDetailName.setText(name)
                        //성별 설정
                        if(gender.equals("0")){
                            profileDetailGender.setText("남자")
                        }else{
                            profileDetailGender.setText("여자")
                        }
                        //생년 월 일 설정
                        profileDetailBirth.setText(birth)
                        //학교 설정
                        profileDetailSchool.setText(school)
                        //키 설정
                        profileDetailHeight.setText(height+"cm")

                        Glide.with(applicationContext).load(thumnail).apply(RequestOptions.circleCropTransform()).into(newteamProfileImg)
                        p = thumnail
                    }
                }


            }
        })



        // 프로필 사진 바꾸기

      /*  changeImg.setOnClickListener(){
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

               Glide.with(newteamProfileImg).load(R.drawable.haein)
            .apply(RequestOptions.circleCropTransform()).into(newteamProfileImg)

        }*/


        saveInfo.setOnClickListener(){
            val checkDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_view, null)

            checkDialog.setView(dialogView)

            val check = checkDialog.show()
            val drawable = resources.getDrawable(R.drawable.dialog)

            dialogView.dialogCancel.setOnClickListener(){
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener(){
                //update profile
                model.putProfile(profileDetailName.text.toString(),
                    profileDetailBirth.text.toString(),
                    profileDetailHeight.text.toString(),
                    p)
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
                check.dismiss()
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