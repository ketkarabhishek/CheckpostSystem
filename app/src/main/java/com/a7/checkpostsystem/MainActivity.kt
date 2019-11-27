package com.a7.checkpostsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings



        Handler().postDelayed(Runnable {
            checkAuth()
        }, 2000)


    }

    fun checkAuth(){
        if (auth.currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }
        else{
            val uid = FirebaseAuth.getInstance().uid
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid", uid)
                .limit(1).get().addOnSuccessListener {
                    for(doc in it){

                        if (doc.contains("isAdmin") && doc.getBoolean("isAdmin")!!){
                            startActivity(Intent(this, AdminActivity::class.java))
                            break;
                        }
                        else{
                            val intent = Intent(this, UserHomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

        }

    }


}
