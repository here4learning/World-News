package org.test.news.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.test.news.OnItemClickListener
import org.test.news.R
import org.test.news.databinding.ItemNewsBinding
import org.test.news.domain.model.NewsItem


@SuppressLint("NotifyDataSetChanged")
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var mNewsList: List<NewsItem>? = null
    private var mOnItemClickClickListener: OnItemClickListener<NewsItem>? = null
    private var mOnItemBookMarkClickClickListener: OnItemClickListener<NewsItem>? = null

    fun setOnItemClickListener(listener: OnItemClickListener<NewsItem>?) {
        mOnItemClickClickListener = listener
    }

    fun setOnItemBookMarkClickListener(listener: OnItemClickListener<NewsItem>?) {
        mOnItemBookMarkClickClickListener = listener
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

    fun setData(list: List<NewsItem>?) {
        mNewsList = list
        notifyDataSetChanged()
    }

    fun setStableId() {
        if (!hasObservers()) {
            setHasStableIds(true)
        }
    }

    override fun getItemId(position: Int): Long {
        return mNewsList!![position].id.toLong()
    }

    override fun getItemCount(): Int {
        return mNewsList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(mNewsList!![position])
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: NewsItem) {
            with(item) {
                binding.textViewDescription.text = description
                binding.textViewTitle.text = name
                binding.imageViewBookmark.setImageResource(if(isBookmarked) R.drawable.ic_bookmark else  R.drawable.ic_unbookmark)
                binding.imageViewBookmark.setOnClickListener {
                    mOnItemBookMarkClickClickListener?.onClick(adapterPosition,item)
                }
                binding.root.setOnClickListener {
                    mOnItemClickClickListener?.onClick(adapterPosition,item)
                }
            }
        }
    }
}
