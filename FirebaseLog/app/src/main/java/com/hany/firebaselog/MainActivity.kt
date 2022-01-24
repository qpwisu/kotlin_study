package com.hany.firebaselog

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hany.firebaselog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.btn3.setOnClickListener {
            val intent1 = Intent(this, SignActivity::class.java)
            startActivity(intent1)
        }
        binding.btn1.setOnClickListener{
            var email= binding.idtxt.text.toString()
            var password = binding.passtxt.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("qqqq", "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, SubActivity::class.java)
                        intent.putExtra("user",email)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("qqqq", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }

        }
    }
//    //활동을 초기화할 때 사용자가 현재 로그인되어 있는지 확인합니다.
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            Log.d("ds","sds")
//        }
//    }
}