package com.a7.checkpostsystem.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.a7.checkpostsystem.LoginActivity
import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.fragments.DetailsDialog
import com.a7.checkpostsystem.models.Noc
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.admin_noc_list_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class AdminNocAdapter(val nocList: List<Noc>, val fm: FragmentManager) : RecyclerView.Adapter<AdminNocAdapter.NocHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NocHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.admin_noc_list_item, parent, false)
        return NocHolder(v, nocList, fm)
    }

    override fun getItemCount(): Int {
        return nocList.size
    }

    override fun onBindViewHolder(holder: NocHolder, position: Int) {
        val  noc = nocList.get(position)
        holder.itemView.state.text = noc.state
        holder.itemView.regNo.text = noc.regNo
        holder.itemView.status_text.text = when(noc.status){
            0 -> "Pending"
            1 -> "Granted"
            -1 -> "Rejected"
            else -> "Unknown"
        }
        val format = SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)
        if (noc.applyDate != null){
            val date: String = format.format(noc.applyDate.toDate())
            holder.itemView.apply_date.text = date
        }
    }

    class NocHolder(itemView: View, list: List<Noc>, fm: FragmentManager) : RecyclerView.ViewHolder(itemView){

        init {
            val db = FirebaseFirestore.getInstance().collection("noc")

            itemView.details.onClick {
                val dialog = DetailsDialog(null, list[adapterPosition])
                dialog.show(fm, "DetailsDialog")
            }
            
            itemView.change_status.onClick {
                val statusList = listOf("Accept", "Reject")
                itemView.context.selector("Change Status", statusList) {dialogInterface, i ->
                    val noc = list[adapterPosition]
                    if (i == 0){

                        val calendar = Calendar.getInstance()
                        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)

                        val validity = Timestamp(calendar.time)
                        noc.validity = validity
                        noc.status = 1


                    }
                    else{
                        noc.status = -1
                        noc.validity = null
                    }
                    db.document(noc.appId.toString()).set(noc)
                        .addOnSuccessListener {
                            itemView.context.toast("Updated Successfully!")
                        }
                        .addOnFailureListener {
                            itemView.context.toast("Updated failed!")
                        }

                }
            }
        }
    }
}