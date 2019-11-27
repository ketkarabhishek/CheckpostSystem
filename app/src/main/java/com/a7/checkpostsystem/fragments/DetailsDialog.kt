package com.a7.checkpostsystem.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.models.Noc
import com.a7.checkpostsystem.models.Permit
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_details.view.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsDialog(val permit: Permit? = null, val noc: Noc? = null) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val db = FirebaseFirestore.getInstance().collection("vehicles")
        val builder = AlertDialog.Builder(context)

        val dialogView = activity!!.layoutInflater.inflate(R.layout.dialog_details, null)

        val format = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)

        var regNo: String? = null

        if (permit != null){
            regNo = permit.regNo
            dialogView.type.text = when(permit.type){
                1 -> "Type 1"
                2 -> "Type 2"
                3 -> "Type 3"
                else -> "Type 4"
            }

            dialogView.permit_id.text = permit.appId

            dialogView.status.text = when(permit.status){
                0 -> "Pending..."
                1 -> "Granted"
                -1 -> "Rejected"
                else -> "Unknown"
            }

            val date: String = format.format(permit.applyDate?.toDate())
            dialogView.apply_date.text = date

            if (permit.validity != null) {
                val validity: String = format.format(permit.validity?.toDate())
                dialogView.validity.text = validity
            }

            dialogView.regNo.text = permit.regNo

        }

        else if (noc != null){
            regNo = noc.regNo
            dialogView.type.text = noc.state

            dialogView.permit_id.text = noc.appId

            dialogView.status.text = when(noc.status){
                0 -> "Pending..."
                1 -> "Granted"
                -1 -> "Rejected"
                else -> "Unknown"
            }

            val date: String = format.format(noc.applyDate?.toDate())
            dialogView.apply_date.text = date

            if (noc.validity != null) {
                val validity: String = format.format(noc.validity?.toDate())
                dialogView.validity.text = validity
            }

            dialogView.regNo.text = noc.regNo
        }



        db.whereEqualTo("regNo", regNo).get().addOnSuccessListener {
            for (doc in it){
                dialogView.owner.text = doc.get("owner").toString()
                dialogView.model.text = doc.get("model").toString()

            }
        }

        builder.setView(dialogView)

        return builder.create()
    }

}