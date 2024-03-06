package com.example.myfamily

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfamily.databinding.ItemMemberBinding

class MemberAdapter(private val listMembers: List<MemberModel>) :
    RecyclerView.Adapter<MemberAdapter.viewholder>() {
    class viewholder(private val item: ItemMemberBinding) : RecyclerView.ViewHolder(item.root) {
//        val imgUser = item.imgUser
        val userName = item.userName
        val userAdress = item.userAdress
        val battery = item.battery
        val navigation = item.navigation

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.viewholder {

        val inflater = LayoutInflater.from(parent.context)
        val item = ItemMemberBinding.inflate(inflater, parent, false)
        return viewholder(item)

    }

    override fun onBindViewHolder(holder: MemberAdapter.viewholder, position: Int) {
        val item = listMembers[position]
        holder.userName.text = item.name
        holder.userAdress.text = item.userAdress
        holder.battery.text = item.battery.toString()
        holder.navigation.text = item.navigation


    }

    override fun getItemCount(): Int {
        return listMembers.size
    }
}