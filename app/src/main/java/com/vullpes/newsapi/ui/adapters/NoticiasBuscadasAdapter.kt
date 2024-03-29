package com.vullpes.newsapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vullpes.newsapi.databinding.NewsItemBinding
import com.vullpes.newsapi.domain.model.Article

class NoticiasBuscadasAdapter(
    private val saveArticle:(Article) -> Unit,
    private val openArticle: (String) -> Unit
): PagingDataAdapter<Article, NoticiasBuscadasAdapter.NoticiasBuscadasViewHolder>(REPO_COMPARATOR) {

    companion object{
        val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onBindViewHolder(holder: NoticiasBuscadasViewHolder, position: Int) {
        val newsArticle = getItem(position)
        newsArticle?.let {
            holder.bind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiasBuscadasViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticiasBuscadasViewHolder(binding, saveArticle, openArticle)
    }

    class NoticiasBuscadasViewHolder(private val binding: NewsItemBinding,
                                     private val saveArticle:(Article) -> Unit,
                                     private val openArticle: (String) -> Unit
        ): RecyclerView.ViewHolder(binding.root){
        fun bind(newsArticle: Article) {
            with(newsArticle) {
                binding.article = newsArticle
                binding.btnSaveButton.setOnClickListener {
                    saveArticle(newsArticle)
                }

                binding.newsItem.setOnClickListener {
                    openArticle(newsArticle.url)
                }
            }
        }

    }


}