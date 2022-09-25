package com.example.news_mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news_mvvm.databinding.ItemNewsBinding
import com.example.news_mvvm.ui.models.Article

class NewsAdapter(private val listener: OnClickListener? = null) :
    RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class NewsVH(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsVH(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            Glide.with(this.root).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            holder.itemView.setOnClickListener {
                listener?.onClick(article)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    interface OnClickListener {
        fun onClick(article: Article)
    }

}