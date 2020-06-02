package com.newsapp.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.newsapp.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    var pageError: Boolean = false;
    var loadURL: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        loadURL = intent.extras?.getString("url") ?: ""
        loadURLToWebview()

        click_to_refresh.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                loadURLToWebview();
            }
        })

        webView.webViewClient = (object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (pageError) {
                    onPageError()
                } else {
                    onPageSucess()
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                pageError = true;
            }

        })


    }

    fun loadURLToWebview() {
        if (!loadURL.isBlank()) {
            webView.loadUrl(loadURL)
            progressBar.visibility = View.VISIBLE
            oops_des.visibility = View.GONE
            click_to_refresh.visibility = View.GONE
        }
    }

    fun onPageError() {
        pageError = false;
        progressBar.visibility = View.GONE
        oops_des.visibility = View.VISIBLE
        click_to_refresh.visibility = View.VISIBLE
        webView.visibility = View.GONE
    }

    fun onPageSucess() {
        progressBar.visibility = View.GONE
        oops_des.visibility = View.GONE
        click_to_refresh.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
