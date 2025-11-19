package com.example.newsmobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)
        setupRecyclerView()

        fetchNews()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchNews() {
        lifecycleScope.launch {
            showLoading()
            try {
                val response = RetrofitClient.instance.getTopHeadlines()
                showArticles(response.articles)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching news", e)
                showError("An error occurred: ${e.message}")
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.GONE
    }

    private fun showArticles(articles: List<Article>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE

        newsAdapter = NewsAdapter(articles) { article ->
            val intent = Intent(this@MainActivity, ArticleActivity::class.java)
            intent.putExtra(ArticleActivity.EXTRA_URL, article.url)
            startActivity(intent)
        }
        recyclerView.adapter = newsAdapter
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = message
    }
}