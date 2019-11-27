package com.a7.checkpostsystem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.models.Noc
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.noc_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class NocAdapter(val nocList: List<Noc>): RecyclerView.Adapter<NocAdapter.NocHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NocHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.noc_list_item, parent, false)
        return NocHolder(v)
    }

    override fun getItemCount(): Int {
        return nocList.size
    }

    override fun onBindViewHolder(holder: NocHolder, position: Int) {
        val noc = nocList.get(position)
        holder.itemView.state.text = noc.state
        holder.itemView.status.text = when(noc.status){
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

        val validity : Timestamp? = noc.validity

        val format = SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)
        if (validity != null){
            val date: String = format.format(validity?.toDate())
            holder.itemView.validity.text = date
        }


    }


    class NocHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}