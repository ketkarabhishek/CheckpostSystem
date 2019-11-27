package com.a7.checkpostsystem

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btn_regpage.onClick {
            startActivity<RegistrationActivity>()
        }

        btn_submit.onClick {
            login_progress.visibility = View.VISIBLE
            submit()
        }

    }

    fun submit(){
        val emailText = email.text.toString();
        val passwordText = password.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passwordText)
            .addOnSuccessListener{

                FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid", it.user.uid)
                    .limit(1).get().addOnSuccessListener {
                        for(doc in it){
                            if (doc.contains("isAdmin") && doc.getBoolean("isAdmin")!!){
                                val intent = Intent(this, AdminActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                break;
                            }
                            else{
                                val intent = Intent(this, UserHomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }

                        }
                    }





            }
            .addOnFailureListener{
                toast("Login Failed!")
            }
    }
}
