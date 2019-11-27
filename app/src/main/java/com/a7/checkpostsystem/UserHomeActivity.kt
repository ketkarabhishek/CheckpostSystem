package com.a7.checkpostsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.a7.checkpostsystem.adapters.ViewPagerAdapter
import com.a7.checkpostsystem.fragments.ProfileFragment
import com.a7.checkpostsystem.fragments.VehiclesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_home.*
import org.jetbrains.anko.toast

class UserHomeActivity : AppCompatActivity() {

    private var backPressedOnce: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        setSupportActionBar(toolbar)
        setTitle("Home | Vehicles")

        //Load default fragment
        loadFragment(VehiclesFragment.newInstance())

        //Bottom nav listener
        nav_bottom.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.vehicles -> {
                    loadFragment(VehiclesFragment.newInstance())
                    setTitle("Home | Vehicles")
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment.newInstance())
                    setTitle("Home | Profile")
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }

        }



//        val adapter = ViewPagerAdapter(supportFragmentManager)
//        viewpager.adapter = adapter
//
//        tablayout.setupWithViewPager(viewpager)
    }

    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
            return
        }

        this.backPressedOnce = true
        toast("Press back again to exit")
        Handler().postDelayed(Runnable { backPressedOnce = false }, 2000)
    }

    //Fragment helper
    fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment)
            .commit()
    }


}
