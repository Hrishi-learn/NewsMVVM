package com.hrishi.newsappmvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hrishi.newsappmvvm.databinding.ItemArticlePreviewBinding
import com.hrishi.newsappmvvm.ui.models.Article

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    class ViewHolder(binding: ItemArticlePreviewBinding):RecyclerView.ViewHolder(binding.root){
        val image = binding.ivArticleImage
        val title=binding.tvTitle
        val source=binding.tvSource
        val desc=binding.tvDescription
        val published=binding.tvPublishedAt
    }
    private val callback = object:DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }
    val differ = AsyncListDiffer(this,callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    private lateinit var onItemClickListener:(Article)->Unit?
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.desc.text=article.description
        holder.published.text=article.publishedAt
        holder.title.text=article.title
        holder.source.text=article.source.name
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.image)
        }
        holder.itemView.setOnClickListener {view->
            onItemClickListener.let {
                it(article)
            }
        }
    }
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}