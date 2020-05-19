package com.example.doubtnut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.doubtnut.R
import com.example.doubtnut.responsemodel.Article
import kotlinx.android.synthetic.main.child_headline.view.*
import java.util.ArrayList

class HeadlineAdapter(var list: ArrayList<Article>):RecyclerView.Adapter<HeadlineAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.child_headline, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.bindItems(list[position])

    }



    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        fun bindItems(article: Article) {

            itemView.headlineTitle.text=article.title

            Glide.with(itemView.context).load(article.urlToImage).diskCacheStrategy(
                DiskCacheStrategy.RESOURCE)
                .into(itemView.headlineImage)

        }

    }
}