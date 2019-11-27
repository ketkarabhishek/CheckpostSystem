package com.a7.checkpostsystem.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a7.checkpostsystem.LoginActivity
import com.a7.checkpostsystem.MainActivity

import com.a7.checkpostsystem.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ProfileFragment : Fragment() {

    companion object{
        fun newInstance(): ProfileFragment{
            return ProfileFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_logout.onClick {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val uid = FirebaseAuth.getInstance().uid
        FirebaseFirestore.getInstance().collection("users")
            .whereEqualTo("uid", uid).limit(1).addSnapshotListener { querySnapshot, exception ->
                if (querySnapshot != null) {
                    for (doc in querySnapshot){
                        name.text = doc["name"].toString()
                        phone.text = doc["phone"].toString()
                        //text_uid.text = uid
                    }
                }
            }
    }
}
