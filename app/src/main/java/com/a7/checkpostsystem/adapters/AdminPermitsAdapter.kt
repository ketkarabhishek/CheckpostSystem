package com.a7.checkpostsystem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.fragments.DetailsDialog
import com.a7.checkpostsystem.models.Permit
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.admin_permit_list_item.view.*
import kotlinx.android.synthetic.main.noc_list_item.view.*
import kotlinx.android.synthetic.main.permits_list_item.view.*
import kotlinx.android.synthetic.main.permits_list_item.view.type
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class AdminPermitsAdapter(val permitsList: List<Permit>,val fm: FragmentManager) : RecyclerView.Adapter<AdminPermitsAdapter.PermitsHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermitsHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.admin_permit_list_item, parent, false)
        return PermitsHolder(v, permitsList, fm)

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

        holder.itemView.status_text.text = when(permit.status){
            0 -> "Pending"
            1 -> "Granted"
            -1 -> "Rejected"
            else -> "Unknown"
        }

        holder.itemView.type.text = type
        holder.itemView.regNo.text = permit.regNo
        val format = SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)
        if (permit.applyDate != null){
            val date: String = format.format(permit.applyDate.toDate())
            holder.itemView.apply_date.text = date
        }
    }

    class PermitsHolder(itemView: View, list: List<Permit>, fm: FragmentManager) : RecyclerView.ViewHolder(itemView){

        init {
            val db = FirebaseFirestore.getInstance().collection("permits")

            itemView.details.onClick {
                val dialog = DetailsDialog(list[adapterPosition], null)
                dialog.show(fm, "DetailsDialog")
            }

            itemView.change_status.onClick {
                val statusList = listOf("Accept", "Reject")
                itemView.context.selector("Change Status", statusList) {dialogInterface, i ->
                    val permit = list[adapterPosition]
                    if (i == 0){

                        val calendar = Calendar.getInstance()
                        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)

                        val validity = Timestamp(calendar.time)
                        permit.validity = validity
                        permit.status = 1
                        itemView.status_text.text = "Granted"

                    }
                    else{
                        permit.status = -1
                        permit.validity = null
                        itemView.status_text.text = "Rejected"

                    }
                    db.document(permit.appId.toString()).set(permit)
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