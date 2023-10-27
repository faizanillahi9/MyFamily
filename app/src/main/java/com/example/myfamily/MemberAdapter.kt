package com.example.myfamily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(private val listMembers: List<MemberModel>) :
    RecyclerView.Adapter<MemberAdapter.viewholder>() {
    class viewholder(private val item: View) : RecyclerView.ViewHolder(item) {
        //val imgUser = item.findViewById<ImageView>(R.id.img_user)
        val userName = item.findViewById<TextView>(R.id.userName)
        val userAdress = item.findViewById<TextView>(R.id.user_adress)
        val battery = item.findViewById<TextView>(R.id.battery)
        val navigation = item.findViewById<TextView>(R.id.navigation)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.viewholder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_member, parent, false)
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