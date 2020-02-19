package com.tingting.ver01.View

import GetProfileResponse
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.UnLinkResponseCallback
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.tingting.ver01.Model.CodeCallBack
import com.tingting.ver01.Model.ModelSignUp
import com.tingting.ver01.Model.ProfileCallBack
import com.tingting.ver01.R
import com.tingting.ver01.SharedPreference.App
import kotlinx.android.synthetic.main.activity_picture_register.*
import kotlinx.android.synthetic.main.activity_profile_detail.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.profile_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class ProfileDetailActivity : AppCompatActivity() {

    var model: ModelSignUp = ModelSignUp(this)
    var p = ""
    var isChangeImage = false;
    lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)


        //init screen data
        model.getProfile(App.prefs.myToken.toString(), object : ProfileCallBack {

            override fun onSuccess2(
                name: String,
                birth: String,
                height: String,
                thumnail: String,
                gender: String,
                school: String,
                myTeamData: List<GetProfileResponse.Data.MyTeam>
            ) {

                val scope = CoroutineScope(Dispatchers.Main)


                runBlocking {
                    Log.d("DetailprofileTest", "프로필 설정")
                    scope.launch {
                        //이름 설정
                        profileDetailName.setText(name)
                        //성별 설정
                        if (gender.equals("0")) {
                            profileDetailGender.setText("남자")
                        } else {
                            profileDetailGender.setText("여자")
                        }
                        //생년 월 일 설정
                        profileDetailBirth.setText(birth)
                        //학교 설정
                        profileDetailSchool.setText(school)
                        //키 설정
                        profileDetailHeight.setText(height)

                        val glideUrl = MainActivity.glide.DecryptUrl(thumnail)

                        this@ProfileDetailActivity?.let { MainActivity.glide.setImage(applicationContext, glideUrl, newteamProfileImg) }

                        p = thumnail
                    }
                }


            }
        })


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


        saveInfo.setOnClickListener() {
            // model


            var regExpId = Regex("^[0-9]+")

            if(!regExpId.matches(profileDetailHeight.text.toString())){
                Toast.makeText(this, "키는 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                if(isChangeImage){
                    model.reviseThumbnail(uri,object :CodeCallBack{

                        override fun onSuccess(code: String, value: String) {
                            if(code.equals("201")){
                                Toast.makeText(applicationContext,"프로필 이미지 수정에 성공하였습니다.",Toast.LENGTH_LONG).show()
                                finish()
                            }else{
                                Toast.makeText(applicationContext,"일시적인 서버 오류입니다. 잠시후 다시 시도해 주세요",Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }


            }
        }

        backButton.setOnClickListener() {
            val checkDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_view, null)

            checkDialog.setView(dialogView)

            val check = checkDialog.show()
            val drawable = resources.getDrawable(R.drawable.dialog)

            dialogView.dialogCancel.setOnClickListener() {
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener() {
                //update profile
                model.putProfile(
                    profileDetailName.text.toString(),
                    profileDetailBirth.text.toString(),
                    profileDetailHeight.text.toString(),
                    p
                )
                finish()
            }

        }

        // 로그아웃

        logout.setOnClickListener() {
            val logoutDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_logout, null)

            logoutDialog.setView(dialogView)
            val check = logoutDialog.show()

            dialogView.dialogCancel.setOnClickListener() {
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener() {
                App.prefs.mypassword = "-1-1"
                App.prefs.myautoLogin = "false"
                finishAffinity()
                check.dismiss()
            }
        }
        //탈퇴하기 임시구현

        delAccout.setOnClickListener() {
            onclickUnlink()

        }


        //DatePicker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            ProfileDetailActivity@this,
            android.R.style.Theme_Holo_Dialog,

            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var month = ""
                var day = ""
                if (monthOfYear < 10) {
                    month = "0" + (monthOfYear + 1).toString()
                } else {
                    month = (monthOfYear + 1).toString()
                }

                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth.toString()
                } else {
                    day = dayOfMonth.toString()
                }

                profileDetailBirth.setText(year.toString() + "-" + month + "-" + day)

            },
            year,
            month,
            day
        )
        // 1990~2002년생
        c.add(Calendar.YEAR, -30)
        dpd.datePicker.minDate = c.timeInMillis
        c.add(Calendar.YEAR, 12)
        dpd.datePicker.maxDate = c.timeInMillis
        // 2000.01.01로 초기화
        dpd.datePicker.init(2000, 1, 1, null)


        //새일 수정이 가능한지 아닌지 몰라서 일단은 안되게 주석.
//        profileDetailBirth.setOnClickListener {
//            dpd.show()
//        }

    }

    private fun pickImageFromGallery() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "권한이 없습니다.", Toast.LENGTH_SHORT).show()
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
                    isChangeImage = true
                    uri = cropImage.uri

                    Glide.with(newteamProfileImg).clear(newteamProfileImg)

                    Glide.with(newteamProfileImg).load(cropImage.uri)
                        .apply(RequestOptions.circleCropTransform()).into(newteamProfileImg)

                }


            }
        } catch (e: Exception) {
            e.printStackTrace()


        }
    }

    fun onclickUnlink() {
        val appendMessage = getString(R.string.com_kakao_confirm_unlink)
        AlertDialog.Builder(this)
            .setMessage(appendMessage)
            .setPositiveButton(getString(R.string.com_kakao_ok_button)
                , DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback() {
                        override fun onSuccess(result: Long?) {
                            redirectSignUpActivity()
                        }

                        override fun onFailure(errorResult: ErrorResult?) {
                            super.onFailure(errorResult)
                        }

                        override fun onSessionClosed(errorResult: ErrorResult?) {
                        }

                        override fun onNotSignedUp() {
                            super.onNotSignedUp()
                        }
                    })
                    dialogInterface.dismiss()
                }).setNegativeButton(
                getString(R.string.com_kakao_cancel_button),
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }).show()

    }

    fun redirectSignUpActivity() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
    }




}