package com.example.tintint_jw.new.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tintint_jw.new.util.UtilDialog

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {
    abstract val layoutRes: Int

    lateinit var binding : T

    private var utilDialog: UtilDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        utilDialog = UtilDialog()

        initView()
    }

    abstract fun initView()

    fun showProgress() {
        utilDialog?.showCustomProgressDialogAboutServer(this)
        window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideProgress() {
        utilDialog?.closeCustomProgressDialog()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}