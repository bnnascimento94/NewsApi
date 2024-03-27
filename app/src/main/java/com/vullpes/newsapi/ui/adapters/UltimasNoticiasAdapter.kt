package com.vullpes.newsapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vullpes.newsapi.R
import com.vullpes.newsapi.databinding.NewsItemBinding
import com.vullpes.newsapi.domain.model.Article

class UltimasNoticiasAdapter(
    private val openArticle:(String) -> Unit,
    private val saveNews: (Article) -> Unit,

): PagingDataAdapter<Article, UltimasNoticiasAdapter.UltimasNoticiasViewHolder>(REPO_COMPARATOR) {

    companion object{
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onBindViewHolder(holder: UltimasNoticiasViewHolder, position: Int) {
        val newsArticle = getItem(position)

        newsArticle?.let {
            holder.bind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UltimasNoticiasViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UltimasNoticiasViewHolder(binding,saveNews, openArticle)
    }

    class UltimasNoticiasViewHolder(private val binding: NewsItemBinding,
                                    private val saveNews: (Article) -> Unit,
                                    private val openArticle: (String) -> Unit
        ): RecyclerView.ViewHolder(binding.root){
        fun bind(newsArticle: Article) {
            with(newsArticle) {
                binding.article = newsArticle
                Glide
                    .with(binding.imgArticle.context)
                    .load(newsArticle.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(binding.imgArticle)

                binding.btnSaveButton.setOnClickListener {
                    saveNews(newsArticle)
                }

                binding.newsItem.setOnClickListener {
                    openArticle(newsArticle.url)
                }
            }
        }

    }
}