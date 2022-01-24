package com.hany.howlstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hany.howlstagram.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth= Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.emailButton.setOnClickListener {
            Log.d("akaa1", "createUserWithEmail:success")

            signinAndSignup()
        }
    }
    fun signinAndSignup(){
        Log.d("akaa2", "createUserWithEmail:success")

        auth.createUserWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.passwordEdittext.toString())
            .addOnCompleteListener(this) {
            task-> Log.d("akaa3", "$task createUserWithEmail:success")
            if(task.isSuccessful) {
                //아이디가 생성됬을떄
                Log.d("akaa3", "createUserWithEmail:success")

                moveMainPage((task.result?.user))
            }else if(task.exception?.message.isNullOrEmpty()){
                //틀렸을떄 에러 메세
                Log.d("akaa4", "createUserWithEmail:success")

                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            }else{
                //아이디 생성도 아니고 에러도 아닐떄 호출
                signinEmail()
            }
        }
    }
    fun signinEmail(){
        auth.signInWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.passwordEdittext.toString())
            .addOnCompleteListener(this) { task->
                if(task.isSuccessful) {
                    //아이디가 생성됬을떄
                    Log.d("akaa5", "createUserWithEmail:success")

                    moveMainPage((task.result?.user))

                }else{
                    Log.d("akaa6", "createUserWithEmail:success")

                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }
    //로그인 성공시 다음페이지로 이동
    fun moveMainPage(user: FirebaseUser?){
        if(user!=null){
            startActivity((Intent(this,MainActivity::class.java)))
        }
    }
}