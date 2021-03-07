package com.example.rhyustagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import com.example.rhyustagram.MainActivity as RhyustagramMainActivity

class LoginActivity : AppCompatActivity() {
    // 회원가입 설정
    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        email_login_button.setOnClickListener {
            signinAndSignup()
        }
    }

    fun signinAndSignup(){
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())?.addOnCompleteListener {
            task ->
            if (task.isSuccessful) {
                // 가입 시 필요한 부분
                moveMainPage(task.result?.user)
            }else if(task.exception?.message.isNullOrEmpty()){
                // 로그인 에러가 발생했을 때
                Toast.makeText(this,task.exception?.message, Toast.LENGTH_LONG).show()
            }else{
                //로그인할 때
                signinEmail()
            }
        }
    }

    fun signinEmail(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())?.addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                // 가입 시 필요한 부분
                moveMainPage(task.result?.user)
            }else if(task.exception?.message.isNullOrEmpty()){
                // 로그인
                Toast.makeText(this,task.exception?.message, Toast.LENGTH_LONG).show()
            }else{
                // 로그인 에러 메세지
            }
        }
    }

    fun moveMainPage(user:FirebaseUser?){
        // 다음 페이지로 넘어가는 intent 콜
        if(user != null) {
            startActivity(Intent(this, RhyustagramMainActivity::class.java))
        }
    }
}