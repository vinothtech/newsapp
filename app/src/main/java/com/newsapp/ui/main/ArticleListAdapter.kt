package com.newsapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.data.model.Articles
import com.newsapp.databinding.ItemLayoutBinding

class ArticleListAdapter(val articleList: ArrayList<Articles>, var listener: ItemClickListener) :
    RecyclerView.Adapter<ArticleListAdapter.ArticleHolder>() {

    interface ItemClickListener {
        fun onItemClicked(article: Articles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {

        val itemBinding =
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ArticleHolder(itemBinding)
    }

    fun updateList(newList: List<Articles>) {
        articleList.clear();
        articleList?.addAll(newList);
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bindTo(articleList.get(position))
        holder.itemView.setOnClickListener({ v -> listener.onItemClicked(articleList.get(position)) })
    }

    class ArticleHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(article: Articles) {
            binding.data = article
        }

    }

}