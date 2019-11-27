package com.a7.checkpostsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a7.checkpostsystem.models.Vehicle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_vehicle_form.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class VehicleFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_form)

        setSupportActionBar(toolbar)
        setTitle("Add Vehicle")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var owner:String? = null
        var regNo: String? = null
        var wheels: Int? = null
        var type: Int? = null
        var model: String? = null
        val uid: String? = FirebaseAuth.getInstance().uid


        val typeList = listOf("Motor Bike", "Auto", "Motor Car", "Bus", "Truck")

        edit_type.onClick {
            selector("Wheels", typeList) { dialogInterface, i ->
                edit_type.setText(typeList[i])
                type = i+1
            }
        }

        btn_submit.onClick {
            owner = edit_owner.text.toString()
            regNo = edit_regno.text.toString()
            model = edit_model.text.toString()
            FirebaseFirestore.getInstance().collection("vehicles")
                .add(Vehicle(owner, regNo, 4, type, model, uid)).addOnSuccessListener {
                    toast("Vehicle Added Successfully")
                    finish()
                }
        }



    }
}
