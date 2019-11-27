package com.a7.checkpostsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a7.checkpostsystem.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_user_home.*
import kotlinx.android.synthetic.main.activity_vehicle_services.*
import kotlinx.android.synthetic.main.activity_vehicle_services.toolbar

class VehicleServicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_services)

        setSupportActionBar(toolbar)
        setTitle("Vehicle Services")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //val regNo: String? = intent.extras.getString("regNo")
        val adapter = ViewPagerAdapter(supportFragmentManager, intent.extras.getString("regNo"))
        viewpager.adapter = adapter

        tablayout.setupWithViewPager(viewpager)
    }
}
