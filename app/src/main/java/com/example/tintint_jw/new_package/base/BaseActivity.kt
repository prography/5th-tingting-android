package com.example.tintint_jw.new_package.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tintint_jw.new_package.util.network.no.NetworkStateReceiverListener
import com.example.tintint_jw.new_package.util.network.no.NetworkUtils
import com.example.tintint_jw.new_package.util.UtilDialog

// 사용 방법 아래!!!
//class MainActivity : BaseActivity(), NetworkStateReceiverListener {
//    override fun networkConnectivityChanged() {
//        if (isConnected) {
//            //show that that the network is back
//        } else {
//            //show that that the network was lost
//        }
//    }
//}

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {
    abstract val layoutRes: Int

    lateinit var binding : T

    private var utilDialog: UtilDialog? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private val networkUtils by lazy {
        NetworkUtils(
            applicationContext
        ) /*important to be applicationContext to prevent memoryLeak*/
    }
    private val connectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    protected val NetworkStateReceiverListener.isConnected: Boolean // can be used by childs
        get() {
            this as BaseActivity<*> // only accessible from child class, so cast i safe here
            return networkUtils.isConnected()
        }

    private fun registerConnectivityMonitoring(listener: NetworkStateReceiverListener) {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                listener.networkConnectivityChanged()
            }

            override fun onLost(network: Network?) {
                listener.networkConnectivityChanged()
            }
        }
        this.networkCallback = networkCallback
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    private fun unregisterConnectivityMonitoring() {
        val networkCallback = this.networkCallback ?: return
        connectivityManager.unregisterNetworkCallback(networkCallback)
        this.networkCallback = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        utilDialog = UtilDialog()

        initView()
        if (this is NetworkStateReceiverListener)
            registerConnectivityMonitoring(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this is NetworkStateReceiverListener)
            unregisterConnectivityMonitoring()
    }

    override fun onResume() {
        super.onResume()
        if (this is NetworkStateReceiverListener && !isConnected)
            this.networkConnectivityChanged()// call to show no network banner on activity resume
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