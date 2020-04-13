package com.tingting.ver01.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.tintint_jw.Model.Profile.SignUpKakaoRequest
import com.tingting.ver01.dataBase.profileFragmentDataBase
import com.tingting.ver01.view.Auth.PictureRegisterActivity
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.model.Auth.CheckDuplicate.ID.DuplicateIdResponse
import com.tingting.ver01.model.Auth.CheckDuplicate.Nickname.DuplicateNameResponse
import com.tingting.ver01.model.Auth.Findidpw.*
import com.tingting.ver01.model.Auth.Login.Kakao.LoginKakaoResponse
import com.tingting.ver01.model.Auth.Login.Local.LoginLocalRequest
import com.tingting.ver01.model.Auth.Login.Local.LoginLocalResponse
import com.tingting.ver01.model.Auth.SignUp.SignUpRequest
import com.tingting.ver01.model.Auth.SignUp.SignUpResponse
import com.tingting.ver01.model.Auth.UploadThumnailResponse
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.model.profile.PatchProfileResponse
import com.tingting.ver01.model.profile.PutProfile
import com.tingting.ver01.sharedPreference.App
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ModelSignUp {
    lateinit var context: Activity

    constructor()

    constructor(con: Activity) {
        context = con
    }


    fun signUP(
        local_id: String, password: String, gender: String,
        name: String, birth: String, authenticated_address: String,
        height: String, ac: Context
    ) {


        val userRequest =
            SignUpRequest(local_id, password, gender, name, birth, authenticated_address, height)
        val call = RetrofitGenerator.create().SignUp(userRequest)

        call.enqueue(object : Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }

            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {


                App.prefs.myToken = response.body()?.data?.token

                response.isSuccessful
                Log.d("TestValue", response.body()?.data?.token.toString())
                Log.d("TestValue", App.prefs.myToken.toString())
                Thread.sleep(1000)
                val intent = Intent(ac, PictureRegisterActivity::class.java)

                val bundle = Bundle(1)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                when (response.code().toString()) {
                    "201" -> startActivity(ac, intent, bundle)
                    "400" -> Toast.makeText(context, "이미 가입된 사용자입니다.", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    fun Login(
        pw: String,
        email: String,
        onResult: (isSuccess: Int, data: LoginLocalResponse?) -> Unit
    ) {

        val loginRequest = LoginLocalRequest(pw, email)
        val call = RetrofitGenerator.create().LoginLocal(loginRequest)

        call.enqueue(object : Callback<LoginLocalResponse> {
            override fun onFailure(call: Call<LoginLocalResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginLocalResponse>,
                response: Response<LoginLocalResponse>
            ) {
                if(response.body()!=null){
                    Log.d("getToken",response.body().toString())
                    onResult(response.code(),response.body())
                }else{
                    onResult(response.code(),null)
                }



            }
        })

    }

    //Get My Profile
    fun getProfile(token: String, profile: ProfileCallBack) {

        val call = RetrofitGenerator.create().getProfile(token)

        call.enqueue(object : Callback<GetProfileResponse> {

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }

            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {

                var body: GetProfileResponse? = response.body()

                profile.onSuccess2(
                    body!!.data.myInfo.name
                    , body.data.myInfo.birth
                    , body.data.myInfo.height.toString()
                    , body.data.myInfo.thumbnail
                    , body.data.myInfo.gender.toString()
                    , body.data.myInfo.schoolName
                    , body.data.myTeamList
                )

                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }


    //Modify My Profile
    fun putProfile(userName: String, birth: String, height: String, thumnail: String) {
        val PutProfile = PutProfile(userName, birth, height, thumnail)
        val call = RetrofitGenerator.create().putProfile(App.prefs.myToken.toString(), PutProfile)

        call.enqueue(object : Callback<PatchProfileResponse> {
            override fun onFailure(call: Call<PatchProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<PatchProfileResponse>,
                response: Response<PatchProfileResponse>
            ) {
                Log.d("ProfileDetailUdpate", "프로필수정")
            }
        })
    }

    fun LoginKakao(id: String?, onResult: (isSuccess: Int, data: LoginKakaoResponse?) -> Unit) {

        val call = RetrofitGenerator.create().LoginKakao(id)

        call.enqueue(object : Callback<LoginKakaoResponse> {

            override fun onFailure(call: Call<LoginKakaoResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginKakaoResponse>,
                response: Response<LoginKakaoResponse>
            ) {

                if (response.isSuccessful) {
                    //토큰 저장.
                    App.prefs.myToken = response.body()?.data?.token

                    onResult(response.code(), response.body()!!)
                } else {
                    onResult(response.code(), null)
                }
            }
        })


    }

    fun CheckDuplicateId(local_id: String, callback: CodeCallBack): Boolean {

        val call = RetrofitGenerator.create().CheckDuplicateId(local_id)

        call.enqueue(object : Callback<DuplicateIdResponse> {
            override fun onFailure(call: Call<DuplicateIdResponse>, t: Throwable) {
                t.printStackTrace()
                call.cancel()

            }

            override fun onResponse(
                call: Call<DuplicateIdResponse>,
                response: Response<DuplicateIdResponse>
            ) {
                var code: Int = response.code()
                var value: String = response.body().toString()
                callback.onSuccess(code.toString(), value)

            }
        })
        return false
    }

    fun CheckDuplicateName(name: String, callback: CodeCallBack): Boolean {

        val call = RetrofitGenerator.create().CheckDuplicateName(name)

        var check = true

        call.enqueue(object : Callback<DuplicateNameResponse> {
            override fun onFailure(call: Call<DuplicateNameResponse>, t: Throwable) {
                t.printStackTrace()
                check = false
            }

            override fun onResponse(
                call: Call<DuplicateNameResponse>,
                response: Response<DuplicateNameResponse>
            ) {
                var code: Int = response.code()
                var value: String = response.message()

                callback.onSuccess(code.toString(), value)
            }
        })
        return check
    }

    fun KakaoSignUp(
        name: String, birth: String, height: String,
        authenticated_address: String, gender: String, callback: ProfileCallBack, ac: Context
    ) {
        val user = SignUpKakaoRequest(name, birth, height, authenticated_address, gender)
        val call = RetrofitGenerator.create().SignUpKakao(App.prefs.myKakaoToken.toString(), user)

        call.enqueue(object : Callback<LoginKakaoResponse> {
            override fun onFailure(call: Call<LoginKakaoResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginKakaoResponse>,
                response: Response<LoginKakaoResponse>
            ) {
                App.prefs.myToken = response.body()?.data?.token

                val intent = Intent(ac, PictureRegisterActivity::class.java)

                val bundle = Bundle(1)

                if (response.body()?.data?.message != null) {
                    callback.kakaoLogin("success")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(ac, intent, bundle)
                } else {
                    callback.kakaoLogin("false")
                }
            }
        })
    }

    fun findId(email: String, back: CodeCallBack) {
        var findIdRequest = FindIdRequest(email)
        val call = RetrofitGenerator.create().findId(findIdRequest)

        call.enqueue(object : Callback<FindIdResponse> {
            override fun onFailure(call: Call<FindIdResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<FindIdResponse>,
                response: Response<FindIdResponse>
            ) {
                var code = response.code()
                var value = response.message().toString()
                back.onSuccess(code.toString(), value)
            }

        })
    }

    fun findPw(email: String, local_id: String, back: CodeCallBack) {
        var findPwRequest = FindPwRequest(email, local_id)
        val call = RetrofitGenerator.create().findPw(findPwRequest)

        call.enqueue(object : Callback<FindPwResponse> {
            override fun onFailure(call: Call<FindPwResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<FindPwResponse>,
                response: Response<FindPwResponse>
            ) {
                var code: Int = response.code()
                var value: String = response.message().toString()

                back.onSuccess(code.toString(), value)
            }

        })
    }

    fun resetPw(token: String, password: String, back: CodeCallBack) {
        var resetPwRequest = ResetPwRequest(password)
        val call = RetrofitGenerator.create().resetPw(token, resetPwRequest)

        call.enqueue(object : Callback<ResetPwResponse> {
            override fun onFailure(call: Call<ResetPwResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ResetPwResponse>,
                response: Response<ResetPwResponse>
            ) {
                var code: Int = response.code()
                var value: String = response.message().toString()

                back.onSuccess(code.toString(), value)
            }

        })
    }


    fun uploadThumbnail(img: Uri, code: CodeCallBack) {

        //실제 주소로 파일을 만드는 부분.
        var file = File(getRealPathFromURIPath(img, context))
        Log.d("chekcfileUrl", file.toString())
        //파일 크기 줄이는 파트
        if (file.length() > 75000) {
            file = saveBitmapToFile(file, 4)
        }
        if (file.length() > 50000) {
            file = saveBitmapToFile(file, 3)
        }

        if (file.length() > 25000) {
            file = saveBitmapToFile(file, 2)
        }

        Log.d("chekcfileUrl", file.length().toString())


        var requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        var part = MultipartBody.Part.createFormData("thumbnail", file.name, requestBody)

        val call = RetrofitGenerator.create().uploadThumbnail(App.prefs.myToken.toString(), part)

        call.enqueue(object : Callback<UploadThumnailResponse> {
            override fun onFailure(call: Call<UploadThumnailResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<UploadThumnailResponse>,
                response: Response<UploadThumnailResponse>
            ) {

                val intent = Intent(context, MainActivity::class.java)

                val bundle = Bundle(1)

                if (response.code() == 201) {
                    startActivity(context, intent, bundle)
                }

            }
        })
    }


    fun reviseThumbnail(img: Uri, code: CodeCallBack) {

        //실제 주소로 파일을 만드는 부분.
        var file = File(getRealPathFromURIPath(img, context))
        Log.d("chekcfileUrl", file.toString())
        //파일 크기 줄이는 파트
        if (file.length() > 75000) {
            file = saveBitmapToFile(file, 4)
        }
        if (file.length() > 50000) {
            file = saveBitmapToFile(file, 3)
        }
        if (file.length() > 25000) {
            file = saveBitmapToFile(file, 2)
        }
        Log.d("chekcfileUrl", file.length().toString())

        var requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        var part = MultipartBody.Part.createFormData("thumbnail", file.name, requestBody)

        val call = RetrofitGenerator.create().uploadThumbnail(App.prefs.myToken.toString(), part)

        call.enqueue(object : Callback<UploadThumnailResponse> {
            override fun onFailure(call: Call<UploadThumnailResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<UploadThumnailResponse>,
                response: Response<UploadThumnailResponse>
            ) {

                if (response.code() == 201) {
                    code.onSuccess("201", "success")
                }

            }
        })
    }


    private fun getRealPathFromURIPath(
        contentURI: Uri,
        activity: Activity
    ): String? {
        val cursor =
            activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    fun saveBitmapToFile(file: File, sampleSize: Int): File {

        try {

            var o = BitmapFactory.Options()
            // BitmapFactory options to downsize the image
            o.inJustDecodeBounds = true
            o.inSampleSize = sampleSize
            // factor of downsizing the image
            var inputStream = FileInputStream(file)

            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 100

            // Find the correct scale value. It should be the power of 2.
            var scale = 1

            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }

            var o2 = BitmapFactory.Options()

            o2.inSampleSize = scale
            inputStream = FileInputStream(file)

            var selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            var outputStream = FileOutputStream(file)

            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)

            return file

        } catch (e: Exception) {
            e.printStackTrace()
            return file
        }
    }


    companion object {
        private var INSTANCE: ModelSignUp? = null
        fun getProfileInstance() = INSTANCE
            ?: ModelSignUp().also {
            INSTANCE = it
        }
    }
}

