package com.a7.checkpostsystem.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.adapters.NocAdapter
import com.a7.checkpostsystem.models.Noc
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_noc.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class NocFragment : Fragment() {

    private val nocList = ArrayList<Noc>()

    private val nocAdapter = NocAdapter(nocList)

    private var mRegNo: String? = null

    val db = FirebaseFirestore.getInstance()

    companion object{
        fun newInstance(regNo: String?): NocFragment{
            val fragment = NocFragment()
            fragment.mRegNo = regNo
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noc_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = nocAdapter
        }

        getData()

        fab_noc.onClick {
            val types = listOf("Telangana", "Andhra Pradesh", "Maharashtra", "Karnataka", "TamilNadu")
            activity?.selector("Permit Type", types) {dialogInterface, i ->
                val nocState = types[i]
                val uid = FirebaseAuth.getInstance().uid
                db.collection("noc").add(Noc(nocState, 0, mRegNo, uid, null, Timestamp.now()))
                    .addOnSuccessListener {
                        context?.toast("Applied Successfully")
                    }
            }
        }
    }

    private fun getData(){

        db.collection("noc").whereEqualTo("regNo", mRegNo)
            .addSnapshotListener { querySnapshot, e ->
                nocList.clear()
                if (e != null) {
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    for (doc in querySnapshot){
                        val item = doc.toObject(Noc::class.java)
                        nocList.add(item)
                        nocAdapter.notifyDataSetChanged()
                    }
                }
            }
    }
}
