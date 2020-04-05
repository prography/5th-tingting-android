package com.tingting.ver01.view.Auth.FindIdAndPw

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tingting.ver01.R
import com.tingting.ver01.model.Auth.Findidpw.FindPwResponse
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.model.PwCallback
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Auth.SetNewPw
import kotlinx.android.synthetic.main.fragment_findpw.*
import kotlinx.android.synthetic.main.fragment_findpw.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class findpw : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    //private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model: ModelSignUp = ModelSignUp(this.activity!!)
        val view: View = inflater.inflate(R.layout.fragment_findpw, container, false)
        view.next.setOnClickListener {
            model.findPw(loginId.text.toString(), accountEmail.text.toString(), object :PwCallback {
                override fun pwResponse(data: FindPwResponse) {
                    App.prefs.mycode = data.data.code

                }
                override fun onSuccess(code: Int) {
                    try{
                        if(code==201){
                            val intent = Intent(activity, SetNewPw::class.java)
                            intent.putExtra("email", accountEmail.text.toString())
                            startActivity(intent)
                        }else if(code==400){
                            Toast.makeText(activity!!.applicationContext, "잘못된 아이디 또는 이메일입니다.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(activity!!.applicationContext, "서버 오류", Toast.LENGTH_LONG).show()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                        Toast.makeText(activity!!.applicationContext, "알 수 없는 오류", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        // Inflate the layout for this fragment
        return view
    }

    fun onButtonPressed(uri: Uri) {
        //listener?.onFragmentInteraction(uri)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            findpw().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
