package com.a7.checkpostsystem.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.VehicleFormActivity
import com.a7.checkpostsystem.adapters.VehicleListAdapter
import com.a7.checkpostsystem.models.Vehicle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_vehicles.*
import kotlinx.android.synthetic.main.vehicle_list_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class VehiclesFragment : Fragment() {

    private val vehicleList = ArrayList<Vehicle>()

    private val vehicleAdapter:VehicleListAdapter? = VehicleListAdapter(vehicleList)

    private var registration: ListenerRegistration? = null

    companion object{
        fun newInstance(): VehiclesFragment{
            return VehiclesFragment()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vehicle_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)

            adapter = vehicleAdapter
        }

        //Firebase data
        getData()


        fab_addvehicle.onClick {
            startActivity(Intent(context, VehicleFormActivity::class.java))
        }

    }

    override fun onPause() {
        super.onPause()
        vehicleList.clear()
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    fun getData(){
        val uid = FirebaseAuth.getInstance().uid
        //activity?.toast(uid.toString())
        val db = FirebaseFirestore.getInstance()
        registration = db.collection("vehicles").whereEqualTo("uid", uid)
            .addSnapshotListener { querySnapshot, e ->
                vehicleList.clear()
                if (e != null) {
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    for (doc in querySnapshot){
                        val item = doc.toObject(Vehicle::class.java)
                        vehicleList.add(item)
                        vehicleAdapter?.notifyDataSetChanged()
                    }
                }
            }
    }


}
