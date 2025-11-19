package com.example.newsmobileapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val articles: MutableList<Article>, private val listener: (Article) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
        holder.itemView.setOnClickListener { listener(article) }
    }

    override fun getItemCount() = articles.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateArticles(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val articleImage: ImageView = itemView.findViewById(R.id.article_image)
        private val articleTitle: TextView = itemView.findViewById(R.id.article_title)
        private val articleSubtitle: TextView = itemView.findViewById(R.id.article_subtitle)
        private val articleSource: TextView = itemView.findViewById(R.id.article_source)
        private val articleAuthor: TextView = itemView.findViewById(R.id.article_author)
        private val articlePublishedDate: TextView = itemView.findViewById(R.id.article_published_date)

        fun bind(article: Article) {
            articleTitle.text = article.title
            articleSubtitle.text = article.description
            articleSource.text = article.source.name
            articleAuthor.text = article.author

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            try {
                val date = inputFormat.parse(article.publishedAt)
                articlePublishedDate.text = date?.let { outputFormat.format(it) }
            } catch (e: Exception) {
                articlePublishedDate.text = ""
            }

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(articleImage)
        }
    }
}