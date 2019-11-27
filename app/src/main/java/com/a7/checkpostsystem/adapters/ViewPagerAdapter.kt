package com.a7.checkpostsystem.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.a7.checkpostsystem.fragments.NocFragment
import com.a7.checkpostsystem.fragments.PermitsFragment
import com.a7.checkpostsystem.fragments.ViewPagerFragment

class ViewPagerAdapter(fm: FragmentManager, regNo: String?) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var mRegNo: String? = null

    init {
        mRegNo = regNo
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> PermitsFragment.newInstance(mRegNo)
            1 -> NocFragment.newInstance(mRegNo)
            else -> ViewPagerFragment.newInstance(mRegNo)

        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Permit"
            1 -> "NOC"
            else -> "Info"
        }
        //return super.getPageTitle(position)
    }
}