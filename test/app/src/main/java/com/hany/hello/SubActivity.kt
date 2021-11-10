package com.hany.hello

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doBeforeTextChanged
import com.hany.hello.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    val binding by lazy {ActivitySubBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //--------------intent로 메세지 받기 -------------------------
        binding.subText.text = intent.getStringExtra("msg")
        binding.closeBtn.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("remsg",binding.editText.text.toString())
            setResult(RESULT_OK,returnIntent)
            finish()
        }
    }

}