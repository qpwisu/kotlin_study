package com.hany.papagoapi

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hany.papagoapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
val CLIENT_ID = "9lrq7i1DWfKBJj3OMVoF"
val CLIENT_SECRET = "x9n9E8YNG6"
val BASE_URL_NAVER_API = "https://openapi.naver.com/"

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL_NAVER_API)
    .addConverterFactory(GsonConverterFactory.create())
    .build()



class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button1.setOnClickListener() {
            var keyword = binding.inputText.text.toString()
            getPapago("ko", "en", keyword)


        }
        binding.button2.setOnClickListener() {
            var keyword = binding.inputText.text.toString()
            getPapago("en", "ko", keyword)
        }
    }


    private fun getPapago(source: String, target: String, text: String) {
        val api = retrofit.create(Api::class.java)
        val callPostTransferPapago = api.transferPapago(
            CLIENT_ID, CLIENT_SECRET,
            source, target, text
        )
        callPostTransferPapago.enqueue(object : Callback<ResultTransferPapago> {
            override fun onResponse(
                call: Call<ResultTransferPapago>,
                response: Response<ResultTransferPapago>
            ) {
                var respon =response.body()?.message?.result?.translatedText
                fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
                //plaintext에는 string 말고 editable이 들어가야되서 위 함수 이용
                binding.inputText.text=respon?.toEditable()



                Log.d(TAG, "성공 : ${respon}}")

            }

            override fun onFailure(call: Call<ResultTransferPapago>, t: Throwable) {
                Log.d(TAG, "실패 : $t")
            }
        })

        Log.d("sss", "" + callPostTransferPapago)
    }
}