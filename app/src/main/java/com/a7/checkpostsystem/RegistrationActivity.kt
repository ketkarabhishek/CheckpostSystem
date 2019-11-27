package com.a7.checkpostsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.a7.checkpostsystem.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registration.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        btn_submit.onClick {
            reg_progress.visibility = View.VISIBLE
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val nameText = name.text.toString()
            val phoneText = phone.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText, passwordText)
                .addOnSuccessListener {
                    FirebaseFirestore.getInstance().collection("users")
                        .add(User(nameText, it.user.uid, phoneText))
                        .addOnSuccessListener {
                            toast("Registered Successfully")
                            startActivity<MainActivity>()
                        }
                        .addOnFailureListener {
                            toast("Failed to add data")
                        }

                }
                .addOnFailureListener {
                    toast("Registration failed")
                    reg_progress.visibility = View.GONE
                }
        }
    }
}
