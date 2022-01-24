package com.hany.txtreader

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hany.txtreader.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import android.net.Uri
import android.provider.DocumentsContract
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import java.io.*
import java.nio.charset.Charset
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.graphics.Rect




var i=0
var wd = 0
var hei =0
val tlist= ArrayList<String>()
var txt= "dasdasdasdasdasdasd"
class MainActivity : AppCompatActivity() {
    val STORAGE_PERMISSION_REQUEST = 200
    val READ_REQUEST_CODE: Int = 42
    var savedUri:Uri? = null
    val REQ_OPEN_FILE = 101
    val REQ_CREATE_FILE = 100
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root);

        binding.button2.setOnClickListener {
            val customView=CustomView(this)
            customView
            binding.fl.addView(customView)
        }


        //storage access framework saf
        binding.button3.setOnClickListener {
            openFile("tt", "text/plain")
//            Log.d("kkk","sda")
//            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
//            startActivityForResult(intent , 100)
//            savedUri?.let { uri ->
//                openFile("tt", "text/plain")
//                Log.d("kkk","sda")
//            }

        }
    }

    fun openFile(filename:String, mimeType:String) {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeType

        }
        startActivityForResult(intent, REQ_OPEN_FILE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQ_OPEN_FILE -> {

                    Log.d("test112","ds")
                    data?.data?.let {uri ->
                        var a = readTextFromUri(uri)
                        txt=a
                        val customView=CustomView(this)

                        binding.fl.addView(customView)
//                        binding.tttt.text=a
                        Log.d("파일내용", readTextFromUri(uri))
                    }
                }
            }
        }
    }

    fun readTextFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream, "utf-16le")).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    tlist.add(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }



    //권한 요청
    fun checkPermission(permissions: Array<String>, permissionRequestNumber:Int){
        val permissionResult = ContextCompat.checkSelfPermission(this, permissions[0])
        when(permissionResult){
            PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
// Go Main Function
            }
            PackageManager.PERMISSION_DENIED -> {
                ActivityCompat.requestPermissions(this, permissions, permissionRequestNumber)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
// Go Main Function
                } else {
                    Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
// Finish() or Show Guidance on the need for permission
                }
            }
        }
    }
    private val EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().toString()

    fun findBooks() {
        doAsync {
            Log.d("test1", "finding books: " + EXTERNAL_STORAGE_PATH)
            val books = ArrayList<String>()

            File(EXTERNAL_STORAGE_PATH).walk().forEach {
                if (it.extension.equals("txt")) {
                    Log.d("test2", it.absolutePath)
                    books.add(it.absolutePath)
                }
            }
        }
    }
    class CustomView(context:Context): View(context){







        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            canvas?.drawColor(Color.WHITE)
            var a=canvas?.height
            val paint = TextPaint()
            paint.color= Color.BLACK
            Log.d("tess1",tlist[0])
            var tsize=40f
            paint.textSize=tsize


//            var num_wd = wd/30f
//            Log.d("avv3",num_wd.toString())
//            var num_hei = hei/30f
            var hei2= hei


            var space =(tsize).toInt()

            Log.d("axx44",height.toString())


            Log.d("axx4",hei2.toString())
            var i2=i
            while (hei2>0){

                var wid = paint.measureText(tlist[i])
                hei2-=(wid/wd+1).toInt()*space
                Log.d("axx4",tlist[i].toString())
                Log.d("axx4i",i.toString())
                Log.d("axx4wid",wid.toString())
                Log.d("axx4wd",wd.toString())
                Log.d("axx4whhh",canvas?.maximumBitmapHeight.toString())

                Log.d("axx4w",paint.measureText(tlist[i]).toString())
                Log.d("axx4hei",hei2.toString())
                if(hei2<0){
                    break
                }
                i+=1
            }
            i-=1
            Log.d("avv5",tlist[i].length.toString())
            txt=tlist.slice(i2..i).joinToString (separator="\n" )
            i+=1
            Log.d("axx3",txt)
            Log.d("axx3",i.toString())
            val textLayout = StaticLayout.Builder.obtain(txt, 0, txt.length, paint, wd) .setAlignment(
                Layout.Alignment.ALIGN_NORMAL) .setLineSpacing(tsize, 0f) .setIncludePad(true).build()
            canvas?.save()
            canvas?.translate(0f, 0f)
            textLayout.draw(canvas)
            canvas?.restore()
            //https://www.ienlab.net/entry/Android-Paint-Canvas%EC%97%90%EC%84%9C-%EC%9E%90%EB%8F%99%EC%9C%BC%EB%A1%9C-%EC%A4%84%EB%B0%94%EA%BF%88%ED%95%98%EA%B8%B0
            //canvas?.drawText (txt, 5, 100, 0f, 100f, paint)
        }
    }

    override fun onWindowFocusChanged( hasFocus:Boolean) {
        wd = binding.fl.width
        hei = binding.fl.height
        Log.d("wdwd",wd.toString())
        Log.d("wdwd",hei.toString())

    }

}



