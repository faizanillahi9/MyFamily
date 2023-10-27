package com.example.myfamily

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InviteAdapter(private val listContact: List<ContactsModel>) :
    RecyclerView.Adapter<InviteAdapter.viewHolder>() {
    class viewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        val name = item.findViewById<TextView>(R.id.name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_invite, parent, false)
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

}