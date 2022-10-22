package com.example.myapplication.WebViewExample

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityWebViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class WebView : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private var myWebView: WebView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myWebView = binding.webViewOne
        myWebView!!.webViewClient = MyWebViewClient()

        progressBar = binding.progressBar
        if (progressBar!!.isVisible)
            progressBar?.visibility = View.INVISIBLE

            if(WebViewFeature.isFeatureSupported(WebViewFeature.START_SAFE_BROWSING))   {
                WebViewCompat.startSafeBrowsing(this, ValueCallback<Boolean> { success ->
                    if(!success)
                        Toast.makeText(this, "Unable to initialize Safe Browsing", Toast.LENGTH_SHORT).show()
                })
            }

        val url = "https://developer.android.com/topic/libraries/architecture/workmanager/advanced/long-running"

        binding.btnLoadUrl.setOnClickListener {
            /**myWebView.settings.javaScriptEnabled = true
            myWebView.settings.domStorageEnabled = true
            myWebView.clearHistory()*/
            myWebView!!.loadUrl(url)
            /**
             * TO programmatically set widget layout
            val layoutParams = binding.webViewONE.layoutParams
            layoutParams.width = 900
            layoutParams.height = 1500
            binding.webViewONE.layoutParams = layoutParams
             */
        }

        binding.btnBack.setOnClickListener {
            if(myWebView!!.canGoBack()) {
                Toast.makeText(this, "Going Back <-->", Toast.LENGTH_SHORT).show()
                myWebView!!.goBack()
            }   else    {
                Toast.makeText(this, "Can not go back!", Toast.LENGTH_SHORT).show()

            }
        }

        binding.btnForward.setOnClickListener {
            if (myWebView!!.canGoForward())   {
                Toast.makeText(this, "Going Forward <-->", Toast.LENGTH_SHORT).show()
                myWebView!!.goForward()
            }   else    {
                Toast.makeText(this, "Can not go forward!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    inner class MyWebViewClient : WebViewClient() {

        /** To handle personally where should specific urls should load, in webView or in Browsers  */
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return false
        }

        /**override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
            if (event?.keyCode == KeyEvent.KEYCODE_BACK && view?.canGoBack() == true) {
                view.goBack()
            }   else if (event?.keyCode == KeyEvent.KEYCODE_FORWARD && view?.canGoForward() == true)    {
                view.goForward()
            }
            super.onUnhandledKeyEvent(view, event)
        }*/
        /**
            Automatically go "back to safety" when attempting to load a website that
            Google has identified as a known threat. An instance of WebView calls
            this method only after Safe Browsing is initialized, so there's no
            conditional logic needed here.                                          */

        override fun onSafeBrowsingHit(
            view: WebView?,
            request: WebResourceRequest?,
            threatType: Int,
            callback: SafeBrowsingResponse?
        ) {
           if(WebViewFeature.isFeatureSupported(WebViewFeature.SAFE_BROWSING_RESPONSE_BACK_TO_SAFETY))  {
               callback?.backToSafety(true)
               // The "true" argument indicates that your app reports incidents like this one to Safe Browsing.
               Toast.makeText(view?.context, "Unsafe web page blocked.", Toast.LENGTH_LONG).show()
           }
        }


        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if(!progressBar!!.isVisible)
                progressBar?.visibility = View.VISIBLE
        }
        /** To perform some action when webpage is pushed on stack when new is opened OR webPage and webView Both are closed */
        override fun onPageFinished(view: WebView?, url: String?) {
        /**    view?.clearHistory()     */
            if(progressBar!!.isVisible)
                progressBar?.visibility = View.INVISIBLE
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            if(progressBar!!.isVisible)
                progressBar?.visibility = View.INVISIBLE
        }



        override fun onRenderProcessGone(
            view: WebView?,
            detail: RenderProcessGoneDetail?
        ): Boolean {
            if(!detail?.didCrash()!!)   {
                Toast.makeText(baseContext, "System killed the WebView process " + " to reclaim memory. Recreating...", Toast.LENGTH_SHORT).show()
                myWebView?.removeView(view)
                myWebView = null
                return true
            }
            return false
        }
    }
}