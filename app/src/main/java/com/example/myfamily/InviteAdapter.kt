package com.example.myfamily

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfamily.databinding.ItemInviteBinding


class InviteAdapter(private val listContact: List<ContactsModel>) :
    RecyclerView.Adapter<InviteAdapter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = ItemInviteBinding.inflate(inflater, parent, false)
        return viewHolder(item)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item = listContact[position]
        // first name is from view-holder
        holder.name.text = item.name

    }

    override fun getItemCount(): Int {
        return listContact.size
    }
    class viewHolder(private val item: ItemInviteBinding) : RecyclerView.ViewHolder(item.root) {
        val name = item.name

    }

}