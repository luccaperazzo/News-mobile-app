package com.example.newsmobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class ArticleActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val url = intent.getStringExtra(EXTRA_URL)
        val webView: WebView = findViewById(R.id.webView)
        if (url != null) {
            webView.loadUrl(url)
        }
    }
}