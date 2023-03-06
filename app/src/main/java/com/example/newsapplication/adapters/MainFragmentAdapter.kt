package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.MainFragment
import com.example.newsapplication.R
import com.example.newsapplication.databinding.NewsItemBinding
import com.example.newsapplication.models.Article

class MainFragmentAdapter(
    val context: MainFragment,
    private val onBookMarkClicked: (Article) -> Unit,
    private val onBookMarkDelete: (Article) -> Unit
):
    ListAdapter<Article,MainFragmentAdapter.NewsViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentAdapter.NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainFragmentAdapter.NewsViewHolder, position: Int) {
        val Article = getItem(position)
        Article?.let {
            holder.bind(it)
        }
    }

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvAuthor.text = article.author
            binding.tvtime.text=article.publishedAt
            Glide.with(context).load(article.urlToImage).placeholder(R.drawable.ic_imageload)
                .into(binding.newsImage)

            binding.favouriteimage.setOnClickListener {
                onBookMarkClicked(article)
                binding.favouriteimage.setImageDrawable(ContextCompat.getDrawable(binding.favouriteimage.context,R.drawable.ic_clicked_star))
            }
           // binding.favouriteimage.setOnClickListener {
             //   onBookMarkDelete(article.id)
              //  binding.favouriteimage.setImageDrawable(ContextCompat.getDrawable(binding.favouriteimage.context,R.drawable.ic_star))
            //}
              binding.root.setOnClickListener {
                //  onNoteClicked(note)
                  onBookMarkDelete(article)

            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}