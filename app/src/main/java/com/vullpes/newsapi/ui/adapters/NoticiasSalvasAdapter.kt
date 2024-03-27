package com.vullpes.newsapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vullpes.newsapi.R
import com.vullpes.newsapi.databinding.NewsItemSavedBinding
import com.vullpes.newsapi.domain.model.Article

class NoticiasSalvasAdapter(
    private val openArticle:(String) -> Unit,
) : RecyclerView.Adapter<NoticiasSalvasAdapter.ViewHolder>() {

    var noticiasSalvas: List<Article> = emptyList()

    fun submitData(noticiasSalvas: List<Article>){
        this.noticiasSalvas = noticiasSalvas
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: NewsItemSavedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.news_item_saved,
            parent,
            false
        )

        return ViewHolder(binding, openArticle)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticiasSalvas[position])
    }

    override fun getItemCount(): Int {
       return noticiasSalvas.size
    }

    class ViewHolder(view: NewsItemSavedBinding,
                     private val openArticle: (String) -> Unit): RecyclerView.ViewHolder(view.root) {
        val binding: NewsItemSavedBinding
        init {
           this.binding = view
        }

        fun bind(newsArticle: Article) {
            with(newsArticle) {
                binding.article = newsArticle
                binding.savedItem.setOnClickListener {
                    openArticle(newsArticle.url)
                }
            }
        }

    }
}