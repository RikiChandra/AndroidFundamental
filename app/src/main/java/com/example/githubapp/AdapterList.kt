package com.example.githubapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class AdapterList(public val listUser: ArrayList<UserGithub>) : RecyclerView.Adapter<AdapterList.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterList.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return ListViewHolder(view)
    }

    interface OnItemClickCallback {
        fun onItemClick(data: UserGithub)
    }


    override fun onBindViewHolder(holder: AdapterList.ListViewHolder, position: Int) {
        val user = listUser[position]
//        Glide.with(holder.itemView.context)
//            .load(user.avatarUrl)
//            .apply(RequestOptions().override(55, 55))
//            .into(holder.imgPhoto)
//        holder.tvName.text = user.login

        with(holder.itemView) {
            Glide.with(holder.itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(55, 55))
                .into(holder.imgPhoto)
            holder.tvName.text = user.login
            holder.itemView.setOnClickListener(){
                val Intent = Intent(context, DetailActivity::class.java)
                Intent.putExtra("login", user.login)
                holder.itemView.context.startActivity(Intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_avatar)
    }


}