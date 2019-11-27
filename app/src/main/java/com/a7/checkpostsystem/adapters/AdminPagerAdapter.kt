package com.a7.checkpostsystem.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.a7.checkpostsystem.fragments.AdminNocFragment
import com.a7.checkpostsystem.fragments.AdminPermitsFragment

class AdminPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> AdminPermitsFragment.newInstance()
            else -> AdminNocFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Permit"
            else -> "NOC"
        }
    }
}