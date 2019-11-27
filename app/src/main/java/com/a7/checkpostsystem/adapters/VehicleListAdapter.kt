package com.a7.checkpostsystem.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.VehicleServicesActivity
import com.a7.checkpostsystem.models.Vehicle
import kotlinx.android.synthetic.main.vehicle_list_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class VehicleListAdapter(val vehicles: List<Vehicle>) : RecyclerView.Adapter<VehicleListAdapter.VehicleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false)
        return VehicleHolder(v, vehicles)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: VehicleHolder, position: Int) {
        val vehicle: Vehicle = vehicles.get(position)
        holder.itemView.model.text = vehicle.model
        holder.itemView.regno.text = vehicle.regNo

    }


    class VehicleHolder(itemView: View, vehicles: List<Vehicle>) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.vehicle_item.onClick {
                Log.d("CHECK", "CHECK")
                val p = adapterPosition
                val regno = vehicles.get(p).regNo

                itemView.context.startActivity<VehicleServicesActivity>("regNo" to regno)
            }
        }
    }
}