package com.a7.checkpostsystem.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.adapters.PermitsAdapter
import com.a7.checkpostsystem.models.Permit
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_permits.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.customView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class PermitsFragment : Fragment() {

    private val permitsList = ArrayList<Permit>()

    private val permitAdapter = PermitsAdapter(permitsList)

    private var mRegNo:String? = null


    var registration: ListenerRegistration? = null

    companion object{
        fun newInstance(regNo: String?) : Fragment{
            val fragment = PermitsFragment()
            fragment.mRegNo = regNo
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permits, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permits_list.apply {
            layoutManager = LinearLayoutManager(context)

            adapter = permitAdapter
        }


        val db = FirebaseFirestore.getInstance()
        val query = db.collection("permits").whereEqualTo("regNo", mRegNo)

        registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            for (dc in snapshot!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val item = dc.document.toObject(Permit::class.java)
                        permitsList.add(item)
                        permitAdapter.notifyDataSetChanged()
                    }
                    DocumentChange.Type.MODIFIED -> return@addSnapshotListener
                    DocumentChange.Type.REMOVED -> return@addSnapshotListener
                }
            }
        }


        fab_permits.onClick {
            val types = listOf("Type1", "Type2", "Type3", "Type4")
            activity?.selector("Permit Type", types) {dialogInterface, i ->
                val permitType = i+1
                val uid = FirebaseAuth.getInstance().uid
                db.collection("permits").add(Permit(permitType, 0, mRegNo, uid, null, Timestamp.now()))
                    .addOnSuccessListener {
                        context?.toast("Applied Successfully")
                    }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        registration?.remove()
        permitsList.clear()
    }



}
