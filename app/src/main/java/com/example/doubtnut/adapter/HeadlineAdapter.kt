package com.example.doubtnut.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.doubtnut.R
import com.example.doubtnut.databinding.ChildHeadlineBinding
import com.example.doubtnut.responsemodel.Article
import java.util.*

class HeadlineAdapter(private var list: ArrayList<Article>):RecyclerView.Adapter<HeadlineAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


      val mBinding:ChildHeadlineBinding=  DataBindingUtil.inflate(LayoutInflater.from(parent.context),
          R.layout.child_headline,parent,false)



        return MyViewHolder(mBinding)

          }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.mbinding.articlemodel=list.get(position)
        holder.mbinding.executePendingBindings()



    }
    class MyViewHolder(var mbinding:ChildHeadlineBinding) : RecyclerView.ViewHolder(mbinding.root) {




    }
}