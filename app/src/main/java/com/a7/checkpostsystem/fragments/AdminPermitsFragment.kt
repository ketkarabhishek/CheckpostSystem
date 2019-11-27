package com.a7.checkpostsystem.fragments


import android.app.ActionBar
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.LinearLayoutManager

import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.adapters.AdminPermitsAdapter
import com.a7.checkpostsystem.models.Permit
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.dialog_search.view.*
import kotlinx.android.synthetic.main.fragment_admin_permits.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast


class AdminPermitsFragment : Fragment() {

    private val permitsList = ArrayList<Permit>()

    private var permitAdapter: AdminPermitsAdapter? = null

    var registration: ListenerRegistration? = null


    companion object{
        fun newInstance() : Fragment{
            val fragment = AdminPermitsFragment()
            return fragment
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_permits, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        admin_permitlist.apply {
            layoutManager = LinearLayoutManager(context)

            permitAdapter = AdminPermitsAdapter(permitsList, childFragmentManager)
            adapter = permitAdapter
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
        val db = FirebaseFirestore.getInstance()

        var query: Query? = null

        if (filter == null){
            query = db.collection("permits")
        }

        else{
            query = db.collection("permits").whereEqualTo("regNo", filter)
            permitsList.clear()
        }


        registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            for (dc in snapshot!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val item: Permit = dc.document.toObject(Permit::class.java)
                        item.appId = dc.document.id
                        permitsList.add(item)
                        permitAdapter?.notifyDataSetChanged()
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
//        permitsList.clear()
//    }
}
