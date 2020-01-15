package com.example.tintint_jw.new.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.tintint_jw.new.util.UtilDialog

abstract class BaseFragment<T : ViewDataBinding>: Fragment() {
    abstract val layoutRes: Int

    lateinit var binding : T

    private var utilDialog: UtilDialog? = null

    abstract fun setView(view: View?, savedInstanceState: Bundle?, arguments: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView(view, savedInstanceState, arguments)
        utilDialog = UtilDialog()
    }

    fun showProgress() {
        utilDialog?.showCustomProgressDialogAboutServer(context)
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideProgress() {
        utilDialog?.closeCustomProgressDialog()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}