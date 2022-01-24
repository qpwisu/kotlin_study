package com.hany.firebaselog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hany.firebaselog.databinding.ActivitySignBinding

class SignActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val binding by lazy { ActivitySignBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth

        //회원 가입
        binding.btn3.setOnClickListener {

            var email= binding.idtxt.text.toString()
            var password = binding.passtxt.text.toString()


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("suc1114", "createUserWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("fail1114", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}