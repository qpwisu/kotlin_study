package com.hany.firebaselog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hany.firebaselog.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    val binding by lazy { ActivitySubBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        val user = intent.getStringExtra("user")
        binding.textView.text=user + "로 로그인 되었습니다"

        val db = Firebase.firestore
        val user1 = hashMapOf(
            "email" to "$user",
        )
// Add a new document with a generated ID


        db.collection("public")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d("sdss", "${document.id} => ${document.data}")
                    binding.textView2.text = document.data.values.toString()

                }
            }
            .addOnFailureListener { exception ->
                Log.w("ssdd", "Error getting documents.", exception)
            }


        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}