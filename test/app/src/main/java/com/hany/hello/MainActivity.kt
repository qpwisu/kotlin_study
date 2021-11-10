package com.hany.hello

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.widget.CompoundButtonCompat
import androidx.core.widget.addTextChangedListener
import com.hany.hello.databinding.ActivityMainBinding
//뷰 바인딩이란 뷰와 소스를 연결하는 방법
class MainActivity : AppCompatActivity() {

    val binding  by lazy{ActivityMainBinding.inflate(layoutInflater) }
    val listener by lazy{CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if(isChecked){
            when(buttonView.id) {
                R.id.checkBox1 -> Log.d("checkbox", "1번선택 ")
                R.id.checkBox2 -> Log.d("checkbox", "2번선택 ")
                R.id.checkBox3 -> Log.d("checkbox", "3번선택 ")
            }
        }
        else{
            when(buttonView.id){
                R.id.checkBox1 ->Log.d("checkbox","1번선택 해제 ")
                R.id.checkBox2 ->Log.d("checkbox","2번선택 해제")
                R.id.checkBox3 ->Log.d("checkbox","3번선택 해제 ")
            }
        }
    }
    }
//----------------------sub액티비티가 종료되면서 값을 돌려준걸 토스트로 출력 -----------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK){
            when(requestCode){
                99 ->{
                    val message =data?.getStringExtra("remsg")
                    Toast.makeText(this,message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate는 xml을 객체화해주는 함수

        setContentView(binding.root)
        //--------------------------------array------------------------------------------
        var int_arr=IntArray(10) //array는 배열의 크기를 미리정해줘야하고 크기는 수정할 수 없다.
        var str_arr=Array(10,{item->""}) //string은 기본타입이 아니기때문에 stringarray가 없고 다음과 같이 이용해야한다
        int_arr[0]=10 // 또는 int_arr.set(0,10) 입력
        int_arr.get(0)// 가져오기

        //----------------------------------edittext-----------------------------
        binding.edittext.addTextChangedListener {
            Log.d("edittext","$it")
        }

        //--------------------------------radiobutton------------------------------------------
        binding.radiog.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radio1 ->Log.d("radiobtn","1번 선택 ")
                R.id.radio2 ->Log.d("radiobtn","2번 선택 ")
                R.id.radio3 ->Log.d("radiobtn","3번 선택 ")
            }
        }

        //--------------------------------체크박스 ------------------------------------------
        binding.checkBox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) Log.d("checkbox","1번 선택")
            else Log.d("checkbox","1번 선택 해제 ") //이렇게하면 모든 채크박스들을 일일이 리스너를 달아줘야한다
        }
        //개선 방식 CompoundButton에 있는 리스너를 직접 작성한다 위에 생성한 listner 확인 (토글버튼,스위치도 동일한 방식으로 컨트롤 가능)

        binding.checkBox1.setOnCheckedChangeListener(listener)
        binding.checkBox2.setOnCheckedChangeListener(listener)
        binding.checkBox3.setOnCheckedChangeListener(listener)

        //--------------------------------레이팅바  ------------------------------------------
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.ratingText.text="$rating"
        }

        //--------------------------------intent 액티비티 이동    ------------------------------------------
//        val intent = Intent(this,SubActivity::class.java)
//        intent= putExtra("msg","dsa") //intent로 값전달 첫번쨰 인자는 태그
//        binding.btnStart.setOnClickListener { startActivity(intent) }

        //--------------------------------버튼을 누르면 intent 내부 번들 저장 공간에 든 값으로 sub액티비티로 전달------------------------------------------
        val intent = Intent(this,SubActivity::class.java)
        binding.btnStart.setOnClickListener {
            intent.putExtra("msg",binding.sendText.text.toString())
            //startActivity(intent)
            //서브 액티비에서 값을 돌려받고싶은때는 아래를 이용
            startActivityForResult(intent,99)
        }
        //--------------------------------컬랙션(list,map,set)------------------------------------------
        //array와 다르게 공간의 크기를 처음에 고정하지 않음
        //list 리스트는 중복된 값 입력가능
        var li = mutableListOf("mon","tue","wed")
        li.add("thu") //추가
        var c=li[0]
        var d= li.get(0)//위와 동일
        li.set(0,"mond")// 수정
        li.removeAt(0) //삭제 빈자리 인덱스로 이동해서 빈자리를 채운다
        li.size //사이즈

        //set 중복허용 x 인덱스 조회 x get x
        var st = mutableSetOf<String>() //중복 허용 x 인덱스x get x
        st.add("sds")
        st.remove("sds")

        //map dictionary와 비슷 키와 쌍으로 이루어져있음
        var mp = mutableMapOf<String,String>()
        mp.put("키1","값1")//추가 or 수정
        mp.get("키1") //키에 대응되는 값 가져오기
        mp.remove("키1")
        var string_list= mutableListOf<String>()//빈리스트 만들기
        //--------------------------------log,if,리스너 ------------------------------------------
        var num :Int =0
        binding.btnsay.setOnClickListener { //리스너의 역할은 버튼을 클릭했을떄 내부의 코드를 실
            num+=1
            if(num==5){
                num=0
            }
            binding.textsay.text = "num= $num" //textsay의 글자를 변경
            Log.d("Hello","num=$num")
        }
        binding.btnsay2.setOnClickListener { //리스너의 역할은 버튼을 클릭했을떄 내부의 코드를 실행
            num-=1
            if(num<0){
                num=0
            }
            binding.textsay.text = "num= $num" //textsay의 글자를 변경
            Log.d("Hello","num=$num")
        }
        var my_age = "24" //변경가능
        val my_name ="장한영입니다" //읽기전용 변경불가
        Log.d("Hello","제이름은 $my_name 나이는 $my_age")

        //------------------for------------------------------
        for (i in 1..10){
            Log.d("hello2","$i")
        }
        //until 종료값 제외
        for (i in 0 until 10){
            Log.d("hello2","$i")
        }
        //step만큼 증가하며 실행
        for (i in 1..10 step 3){
            Log.d("hello2","$i")
        }
        //감소시키기
        for (i in 10 downTo 0){
            Log.d("hello2","$i")
        }
        //배열 for문
        var array_month = arrayOf("jan","feb","mar")
        for(month in array_month){
            Log.d("mon","$month")
        }
        //while, do while,break, continue 있음

        //-------------함수 ----------------------
        //리턴없는함수
        fun str_print(x:String){
            Log.d("str_fun","$x")
        }
        str_print("test")
        //리턴 있는 함수
        fun sum(x:Int,y:Int): Int {
            return x + y
        }
        //파라미터 기본값 정의
        fun param(name:String,age:Int,sex:String="남자 ",height:Int=180 ){
            Log.d("para","$name $age $sex $height")
        }
        param("한영",24)//여기에 sex로 여자를 넣으면 바뀜
        param("한영",24,"여자",height=150) //파라미터를 이름으로 입력가능
    }
}



//----------class-----------------
// 상속 연습
open class Parent {
    var hello:String = "안녕하세요"
    fun sayHello( ) {
        Log.d("inheritance", "${hello}")
    }
}
class Child : Parent( ) {
    fun myHello( ) {
        hello = "Hello"
        sayHello( )
    }
}
// 메서드 오버라이드 연습
open class BaseClass {
    open fun opened() {

    }
    fun notOpend() {

    }
}
class ChildClass: BaseClass( ) {
    override fun opened() {

    }
//    override fun notOpend(){ // 오버라이드 되지 않고 에러가 발생한다.
//
//    }
}
// 프로퍼티 오버라이드 연습
open class BaseClass2 {
    open var opened: String = "I am"
}
class ChildClass2: BaseClass2( ) {
    override var opened: String = "You are"
}
fun String.plus(word:String ) : String{
    return this + word
}