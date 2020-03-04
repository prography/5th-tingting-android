package com.tingting.ver01.FindIdAndPw

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.tingting.ver01.Model.CodeCallBack
import com.tingting.ver01.Model.ModelSignUp
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.fragment_findid.*
import kotlinx.android.synthetic.main.fragment_findid.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class findid : Fragment() {
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
        var model:ModelSignUp = ModelSignUp(this.activity!!)
        var view:View = inflater.inflate(R.layout.fragment_findid, container, false)
        var findId: Button = view.findViewById(R.id.next)

        findId.isEnabled = false

        view.assignedEmail.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                if(s.isValidEmail()){
                    findId.isEnabled = true
                }
                findId.isEnabled = false
                /*var regex = Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}")

                if(regex.matches(view.assignedEmail.text.toString())){
                    findId.isEnabled = true
                }
                findId.isEnabled= false*/
            }

        })

        findId.setOnClickListener {
            model.findId(view.assignedEmail.text.toString(), object:CodeCallBack{
                override fun onSuccess(code: String, value: String) {

                    try{
                        if(code.equals("200")){
                            Toast.makeText(activity, "아이디 찾기 메일을 전송했습니다.", Toast.LENGTH_LONG).show()
                        }else if(code.equals("400")){
                            Toast.makeText(activity, "존재하지 않는 이메일입니다.", Toast.LENGTH_LONG).show()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            })
        }

        return view
    }

    fun onButtonPressed(uri: Uri) {
        //listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        //listener = null
    }

    /*interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }*/

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            findid().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
