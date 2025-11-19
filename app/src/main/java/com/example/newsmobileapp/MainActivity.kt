package com.example.newsmobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchNews()
    }

    private fun fetchNews() {
        lifecycleScope.launch {
            showLoading()
            try {
                val response = RetrofitClient.instance.getTopHeadlines()
                showArticles(response.articles)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching news", e)
                val debugMessage = "Error: ${e.message}\nKey Used: [${BuildConfig.NEWS_API_KEY}]"
                showError(debugMessage)
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

        adapter = NewsAdapter(articles) { article ->
            val intent = Intent(this@MainActivity, ArticleActivity::class.java)
            intent.putExtra(ArticleActivity.EXTRA_URL, article.url)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = message
    }
}