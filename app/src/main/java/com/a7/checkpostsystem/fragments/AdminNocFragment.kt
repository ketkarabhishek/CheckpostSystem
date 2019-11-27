package com.a7.checkpostsystem.fragments


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.adapters.AdminNocAdapter
import com.a7.checkpostsystem.models.Noc
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.dialog_search.view.*
import kotlinx.android.synthetic.main.fragment_admin_noc.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class AdminNocFragment : Fragment() {

    private val nocList = ArrayList<Noc>()

    private var nocAdapter: AdminNocAdapter? = null

    var registration: ListenerRegistration? = null

    companion object{
        fun newInstance() : Fragment{
            val fragment = AdminNocFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_noc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        admin_noclist.apply {
            layoutManager = LinearLayoutManager(context)

            nocAdapter = AdminNocAdapter(nocList, childFragmentManager)
            adapter = nocAdapter


        }

        getData(null)

        fab_search.onClick {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Search")


            val v = layoutInflater.inflate(R.layout.dialog_search, null)

            builder.setView(v)

            builder.setPositiveButton("Search"){ dialog, i ->
                val key = v.edit_search.text.toString()
                getData(key)
            }

            builder.setNegativeButton("Cancel"){dialog, i ->
                dialog.dismiss()
                getData(null)
            }

            builder.show()
        }
    }

    fun getData(filter: String?){
        nocList.clear()
        val db = FirebaseFirestore.getInstance()
        val query: Query?

        if (filter == null){
            query = db.collection("noc")
        }
        else{
            query = db.collection("noc").whereEqualTo("regNo", filter)
        }

        registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            for (dc in snapshot!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val item: Noc = dc.document.toObject(Noc::class.java)
                        item.appId = dc.document.id
                        nocList.add(item)
                        nocAdapter?.notifyDataSetChanged()
                    }
                    DocumentChange.Type.MODIFIED -> return@addSnapshotListener
                    DocumentChange.Type.REMOVED -> return@addSnapshotListener
                }
            }
        }
    }



    //    override fun onPause() {
//        super.onPause()
//        registration?.remove()
//        nocList.clear()
//    }


}
