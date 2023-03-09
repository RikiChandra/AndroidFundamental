package com.example.githubapp.view.detail.follows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.UserGithub

class FollowAdapter(val listUser: ArrayList<UserGithub>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follow = listUser[position]
        with(holder){
            holder.tvName.text = follow.login
            Glide.with(holder.itemView.context)
                .load(follow.avatarUrl)
                .into(holder.imgPhoto)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_avatar)
    }


}
