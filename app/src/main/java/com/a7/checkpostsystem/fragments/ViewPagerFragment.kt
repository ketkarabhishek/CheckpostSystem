package com.a7.checkpostsystem.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.a7.checkpostsystem.R
import com.a7.checkpostsystem.models.Vehicle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerFragment : Fragment() {

    private var args: String? = null

    companion object{
        fun newInstance(text: String?) : Fragment{
            val fragment = ViewPagerFragment()
            fragment.args = text
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_view_pager, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseFirestore.getInstance().collection("vehicles")
            .whereEqualTo("regNo", args).limit(1).addSnapshotListener { snapshot, exception ->
                if (snapshot != null){
                    for (doc in snapshot){
                        val item = doc.toObject(Vehicle::class.java)
                        model.text = item.model
                        regno.text = item.regNo
                        owner.text = item.owner
                        type.text  = when(item.type){
                            1 -> "Motor Bike"
                            2 -> "Auto"
                            3 -> "Motor Car"
                            4 -> "Truck"
                            else -> "Unknown"
                        }
                    }
                }
            }
    }
}
