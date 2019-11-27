package com.a7.checkpostsystem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.models.Permit
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.noc_list_item.view.*
import kotlinx.android.synthetic.main.permits_list_item.view.*
import kotlinx.android.synthetic.main.permits_list_item.view.status
import java.text.SimpleDateFormat
import java.util.*

class PermitsAdapter (val permitsList: List<Permit>): RecyclerView.Adapter<PermitsAdapter.PermitsHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermitsHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.permits_list_item, parent, false)
        return PermitsHolder(v)
    }

    override fun getItemCount(): Int {
        return permitsList.size
    }

    override fun onBindViewHolder(holder: PermitsHolder, position: Int) {
        val permit: Permit = permitsList.get(position)
        var type: String? = null;
        if (permit.type == 1) {type = "Type 1"}
        else if (permit.type == 2) {type = "Type 2"}
        else if (permit.type == 3) {type = "Type 3"}
        else if (permit.type == 4) {type = "Type 4"}

        var mstatus: String? = null
        mstatus = when(permit.status){
            0 -> {
                "Pending..."
            }
            1 -> {
                "Granted"
            }
            -1 -> {
                "Rejected"
            }
            else -> {
                "sdvsdv"
            }
        }
        holder.itemView.type.text = type
        holder.itemView.status.text = mstatus

        val validity : Timestamp? = permit.validity

        val format = SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)
        if (validity != null){
            val date: String = format.format(validity?.toDate())
            holder.itemView.appno.text = date
        }


    }

    class PermitsHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


    }
}