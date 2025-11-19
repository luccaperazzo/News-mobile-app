package com.example.newsmobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class ArticleActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient() // Evita que se abra el navegador externo

        // Forma segura de manejar la URL
        intent.getStringExtra(EXTRA_URL)?.let {
            webView.loadUrl(it)
        }
    }
}